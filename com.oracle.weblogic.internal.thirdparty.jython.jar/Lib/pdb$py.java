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
@Filename("pdb.py")
public class pdb$py extends PyFunctionTable implements PyRunnable {
   static pdb$py self;
   static final PyCode f$0;
   static final PyCode Restart$1;
   static final PyCode find_function$2;
   static final PyCode Pdb$3;
   static final PyCode __init__$4;
   static final PyCode reset$5;
   static final PyCode forget$6;
   static final PyCode setup$7;
   static final PyCode execRcLines$8;
   static final PyCode user_call$9;
   static final PyCode user_line$10;
   static final PyCode bp_commands$11;
   static final PyCode user_return$12;
   static final PyCode user_exception$13;
   static final PyCode interaction$14;
   static final PyCode displayhook$15;
   static final PyCode default$16;
   static final PyCode precmd$17;
   static final PyCode onecmd$18;
   static final PyCode handle_command_def$19;
   static final PyCode do_commands$20;
   static final PyCode do_break$21;
   static final PyCode defaultFile$22;
   static final PyCode do_tbreak$23;
   static final PyCode lineinfo$24;
   static final PyCode checkline$25;
   static final PyCode do_enable$26;
   static final PyCode do_disable$27;
   static final PyCode do_condition$28;
   static final PyCode do_ignore$29;
   static final PyCode do_clear$30;
   static final PyCode do_where$31;
   static final PyCode do_up$32;
   static final PyCode do_down$33;
   static final PyCode do_until$34;
   static final PyCode do_step$35;
   static final PyCode do_next$36;
   static final PyCode do_run$37;
   static final PyCode do_return$38;
   static final PyCode do_continue$39;
   static final PyCode do_jump$40;
   static final PyCode do_debug$41;
   static final PyCode do_quit$42;
   static final PyCode do_EOF$43;
   static final PyCode do_args$44;
   static final PyCode do_retval$45;
   static final PyCode _getval$46;
   static final PyCode do_p$47;
   static final PyCode do_pp$48;
   static final PyCode do_list$49;
   static final PyCode do_whatis$50;
   static final PyCode do_alias$51;
   static final PyCode do_unalias$52;
   static final PyCode print_stack_trace$53;
   static final PyCode print_stack_entry$54;
   static final PyCode help_help$55;
   static final PyCode help_h$56;
   static final PyCode help_where$57;
   static final PyCode help_w$58;
   static final PyCode help_down$59;
   static final PyCode help_d$60;
   static final PyCode help_up$61;
   static final PyCode help_u$62;
   static final PyCode help_break$63;
   static final PyCode help_b$64;
   static final PyCode help_clear$65;
   static final PyCode help_cl$66;
   static final PyCode help_tbreak$67;
   static final PyCode help_enable$68;
   static final PyCode help_disable$69;
   static final PyCode help_ignore$70;
   static final PyCode help_condition$71;
   static final PyCode help_step$72;
   static final PyCode help_s$73;
   static final PyCode help_until$74;
   static final PyCode help_unt$75;
   static final PyCode help_next$76;
   static final PyCode help_n$77;
   static final PyCode help_return$78;
   static final PyCode help_r$79;
   static final PyCode help_continue$80;
   static final PyCode help_cont$81;
   static final PyCode help_c$82;
   static final PyCode help_jump$83;
   static final PyCode help_j$84;
   static final PyCode help_debug$85;
   static final PyCode help_list$86;
   static final PyCode help_l$87;
   static final PyCode help_args$88;
   static final PyCode help_a$89;
   static final PyCode help_p$90;
   static final PyCode help_pp$91;
   static final PyCode help_exec$92;
   static final PyCode help_run$93;
   static final PyCode help_quit$94;
   static final PyCode help_q$95;
   static final PyCode help_whatis$96;
   static final PyCode help_EOF$97;
   static final PyCode help_alias$98;
   static final PyCode help_unalias$99;
   static final PyCode help_commands$100;
   static final PyCode help_pdb$101;
   static final PyCode lookupmodule$102;
   static final PyCode _runscript$103;
   static final PyCode run$104;
   static final PyCode runeval$105;
   static final PyCode runctx$106;
   static final PyCode runcall$107;
   static final PyCode set_trace$108;
   static final PyCode post_mortem$109;
   static final PyCode pm$110;
   static final PyCode test$111;
   static final PyCode help$112;
   static final PyCode main$113;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A Python debugger."));
      var1.setline(3);
      PyString.fromInterned("A Python debugger.");
      var1.setline(7);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("cmd", var1, -1);
      var1.setlocal("cmd", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("bdb", var1, -1);
      var1.setlocal("bdb", var3);
      var3 = null;
      var1.setline(11);
      String[] var5 = new String[]{"Repr"};
      PyObject[] var6 = imp.importFrom("repr", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Repr", var4);
      var4 = null;
      var1.setline(12);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("pprint", var1, -1);
      var1.setlocal("pprint", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(18);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Restart", var6, Restart$1);
      var1.setlocal("Restart", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(24);
      var3 = var1.getname("Repr").__call__(var2);
      var1.setlocal("_repr", var3);
      var3 = null;
      var1.setline(25);
      PyInteger var7 = Py.newInteger(200);
      var1.getname("_repr").__setattr__((String)"maxstring", var7);
      var3 = null;
      var1.setline(26);
      var3 = var1.getname("_repr").__getattr__("repr");
      var1.setlocal("_saferepr", var3);
      var3 = null;
      var1.setline(28);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("run"), PyString.fromInterned("pm"), PyString.fromInterned("Pdb"), PyString.fromInterned("runeval"), PyString.fromInterned("runctx"), PyString.fromInterned("runcall"), PyString.fromInterned("set_trace"), PyString.fromInterned("post_mortem"), PyString.fromInterned("help")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(31);
      var6 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var6, find_function$2, (PyObject)null);
      var1.setlocal("find_function", var9);
      var3 = null;
      var1.setline(57);
      PyString var10 = PyString.fromInterned("\n-> ");
      var1.setlocal("line_prefix", var10);
      var3 = null;
      var1.setline(59);
      var6 = new PyObject[]{var1.getname("bdb").__getattr__("Bdb"), var1.getname("cmd").__getattr__("Cmd")};
      var4 = Py.makeClass("Pdb", var6, Pdb$3);
      var1.setlocal("Pdb", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1237);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, run$104, (PyObject)null);
      var1.setlocal("run", var9);
      var3 = null;
      var1.setline(1240);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, runeval$105, (PyObject)null);
      var1.setlocal("runeval", var9);
      var3 = null;
      var1.setline(1243);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, runctx$106, (PyObject)null);
      var1.setlocal("runctx", var9);
      var3 = null;
      var1.setline(1247);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, runcall$107, (PyObject)null);
      var1.setlocal("runcall", var9);
      var3 = null;
      var1.setline(1250);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, set_trace$108, (PyObject)null);
      var1.setlocal("set_trace", var9);
      var3 = null;
      var1.setline(1255);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, post_mortem$109, (PyObject)null);
      var1.setlocal("post_mortem", var9);
      var3 = null;
      var1.setline(1269);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, pm$110, (PyObject)null);
      var1.setlocal("pm", var9);
      var3 = null;
      var1.setline(1275);
      var10 = PyString.fromInterned("import x; x.main()");
      var1.setlocal("TESTCMD", var10);
      var3 = null;
      var1.setline(1277);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, test$111, (PyObject)null);
      var1.setlocal("test", var9);
      var3 = null;
      var1.setline(1281);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, help$112, (PyObject)null);
      var1.setlocal("help", var9);
      var3 = null;
      var1.setline(1292);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, main$113, (PyObject)null);
      var1.setlocal("main", var9);
      var3 = null;
      var1.setline(1336);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1337);
         var3 = imp.importOne("pdb", var1, -1);
         var1.setlocal("pdb", var3);
         var3 = null;
         var1.setline(1338);
         var1.getname("pdb").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Restart$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Causes a debugger to be restarted for the debugged python program."));
      var1.setline(19);
      PyString.fromInterned("Causes a debugger to be restarted for the debugged python program.");
      var1.setline(20);
      return var1.getf_locals();
   }

   public PyObject find_function$2(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("def\\s+%s\\s*[(]")._mod(var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(0))));
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(34);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("IOError"))) {
            var1.setline(36);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var6;
      }

      var1.setline(38);
      PyInteger var7 = Py.newInteger(1);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(40);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(41);
         var3 = var1.getlocal(3).__getattr__("readline").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(42);
         var3 = var1.getlocal(6);
         PyObject var10000 = var3._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(44);
         if (var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(45);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(4)});
            var1.setlocal(5, var8);
            var3 = null;
            break;
         }

         var1.setline(47);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(48);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(49);
      var4 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Pdb$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(61);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("tab"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$5, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, forget$6, (PyObject)null);
      var1.setlocal("forget", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setup$7, (PyObject)null);
      var1.setlocal("setup", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, execRcLines$8, (PyObject)null);
      var1.setlocal("execRcLines", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_call$9, PyString.fromInterned("This method is called when there is the remote possibility\n        that we ever need to stop in this function."));
      var1.setlocal("user_call", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_line$10, PyString.fromInterned("This function is called when we stop or break at this line."));
      var1.setlocal("user_line", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, bp_commands$11, PyString.fromInterned("Call every command that was set for the current active breakpoint\n        (if there is one).\n\n        Returns True if the normal interaction function must be called,\n        False otherwise."));
      var1.setlocal("bp_commands", var4);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_return$12, PyString.fromInterned("This function is called when a return trap is set here."));
      var1.setlocal("user_return", var4);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_exception$13, PyString.fromInterned("This function is called if an exception occurs,\n        but only if we are to stop at or just below this level."));
      var1.setlocal("user_exception", var4);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, interaction$14, (PyObject)null);
      var1.setlocal("interaction", var4);
      var3 = null;
      var1.setline(213);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, displayhook$15, PyString.fromInterned("Custom displayhook for the exec in default(), which prevents\n        assignment of the _ variable in the builtins.\n        "));
      var1.setlocal("displayhook", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, default$16, (PyObject)null);
      var1.setlocal("default", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, precmd$17, PyString.fromInterned("Handle alias expansion and ';;' separator."));
      var1.setlocal("precmd", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, onecmd$18, PyString.fromInterned("Interpret the argument as though it had been typed in response\n        to the prompt.\n\n        Checks whether this line is typed at the normal prompt or in\n        a breakpoint command list definition.\n        "));
      var1.setlocal("onecmd", var4);
      var3 = null;
      var1.setline(283);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_command_def$19, PyString.fromInterned("Handles one command line during command list definition."));
      var1.setlocal("handle_command_def", var4);
      var3 = null;
      var1.setline(315);
      PyObject var5 = var1.getname("cmd").__getattr__("Cmd").__getattr__("do_help");
      var1.setlocal("do_h", var5);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_commands$20, PyString.fromInterned("Defines a list of commands associated to a breakpoint.\n\n        Those commands will be executed whenever the breakpoint causes\n        the program to stop execution."));
      var1.setlocal("do_commands", var4);
      var3 = null;
      var1.setline(344);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, do_break$21, (PyObject)null);
      var1.setlocal("do_break", var4);
      var3 = null;
      var1.setline(427);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, defaultFile$22, PyString.fromInterned("Produce a reasonable default."));
      var1.setlocal("defaultFile", var4);
      var3 = null;
      var1.setline(434);
      var5 = var1.getname("do_break");
      var1.setlocal("do_b", var5);
      var3 = null;
      var1.setline(436);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_tbreak$23, (PyObject)null);
      var1.setlocal("do_tbreak", var4);
      var3 = null;
      var1.setline(439);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lineinfo$24, (PyObject)null);
      var1.setlocal("lineinfo", var4);
      var3 = null;
      var1.setline(472);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, checkline$25, PyString.fromInterned("Check whether specified line seems to be executable.\n\n        Return `lineno` if it is, 0 if not (e.g. a docstring, comment, blank\n        line or EOF). Warning: testing is not comprehensive.\n        "));
      var1.setlocal("checkline", var4);
      var3 = null;
      var1.setline(493);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_enable$26, (PyObject)null);
      var1.setlocal("do_enable", var4);
      var3 = null;
      var1.setline(510);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_disable$27, (PyObject)null);
      var1.setlocal("do_disable", var4);
      var3 = null;
      var1.setline(527);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_condition$28, (PyObject)null);
      var1.setlocal("do_condition", var4);
      var3 = null;
      var1.setline(552);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_ignore$29, PyString.fromInterned("arg is bp number followed by ignore count."));
      var1.setlocal("do_ignore", var4);
      var3 = null;
      var1.setline(584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_clear$30, PyString.fromInterned("Three possibilities, tried in this order:\n        clear -> clear all breaks, ask for confirmation\n        clear file:lineno -> clear all breaks at file:lineno\n        clear bpno bpno ... -> clear breakpoints by number"));
      var1.setlocal("do_clear", var4);
      var3 = null;
      var1.setline(627);
      var5 = var1.getname("do_clear");
      var1.setlocal("do_cl", var5);
      var3 = null;
      var1.setline(629);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_where$31, (PyObject)null);
      var1.setlocal("do_where", var4);
      var3 = null;
      var1.setline(631);
      var5 = var1.getname("do_where");
      var1.setlocal("do_w", var5);
      var3 = null;
      var1.setline(632);
      var5 = var1.getname("do_where");
      var1.setlocal("do_bt", var5);
      var3 = null;
      var1.setline(634);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_up$32, (PyObject)null);
      var1.setlocal("do_up", var4);
      var3 = null;
      var1.setline(643);
      var5 = var1.getname("do_up");
      var1.setlocal("do_u", var5);
      var3 = null;
      var1.setline(645);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_down$33, (PyObject)null);
      var1.setlocal("do_down", var4);
      var3 = null;
      var1.setline(654);
      var5 = var1.getname("do_down");
      var1.setlocal("do_d", var5);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_until$34, (PyObject)null);
      var1.setlocal("do_until", var4);
      var3 = null;
      var1.setline(659);
      var5 = var1.getname("do_until");
      var1.setlocal("do_unt", var5);
      var3 = null;
      var1.setline(661);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_step$35, (PyObject)null);
      var1.setlocal("do_step", var4);
      var3 = null;
      var1.setline(664);
      var5 = var1.getname("do_step");
      var1.setlocal("do_s", var5);
      var3 = null;
      var1.setline(666);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_next$36, (PyObject)null);
      var1.setlocal("do_next", var4);
      var3 = null;
      var1.setline(669);
      var5 = var1.getname("do_next");
      var1.setlocal("do_n", var5);
      var3 = null;
      var1.setline(671);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_run$37, PyString.fromInterned("Restart program by raising an exception to be caught in the main\n        debugger loop.  If arguments were given, set them in sys.argv."));
      var1.setlocal("do_run", var4);
      var3 = null;
      var1.setline(681);
      var5 = var1.getname("do_run");
      var1.setlocal("do_restart", var5);
      var3 = null;
      var1.setline(683);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_return$38, (PyObject)null);
      var1.setlocal("do_return", var4);
      var3 = null;
      var1.setline(686);
      var5 = var1.getname("do_return");
      var1.setlocal("do_r", var5);
      var3 = null;
      var1.setline(688);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_continue$39, (PyObject)null);
      var1.setlocal("do_continue", var4);
      var3 = null;
      var1.setline(691);
      var5 = var1.getname("do_continue");
      var1.setlocal("do_c", var5);
      var1.setlocal("do_cont", var5);
      var1.setline(693);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_jump$40, (PyObject)null);
      var1.setlocal("do_jump", var4);
      var3 = null;
      var1.setline(710);
      var5 = var1.getname("do_jump");
      var1.setlocal("do_j", var5);
      var3 = null;
      var1.setline(712);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_debug$41, (PyObject)null);
      var1.setlocal("do_debug", var4);
      var3 = null;
      var1.setline(724);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_quit$42, (PyObject)null);
      var1.setlocal("do_quit", var4);
      var3 = null;
      var1.setline(729);
      var5 = var1.getname("do_quit");
      var1.setlocal("do_q", var5);
      var3 = null;
      var1.setline(730);
      var5 = var1.getname("do_quit");
      var1.setlocal("do_exit", var5);
      var3 = null;
      var1.setline(732);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_EOF$43, (PyObject)null);
      var1.setlocal("do_EOF", var4);
      var3 = null;
      var1.setline(738);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_args$44, (PyObject)null);
      var1.setlocal("do_args", var4);
      var3 = null;
      var1.setline(749);
      var5 = var1.getname("do_args");
      var1.setlocal("do_a", var5);
      var3 = null;
      var1.setline(751);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_retval$45, (PyObject)null);
      var1.setlocal("do_retval", var4);
      var3 = null;
      var1.setline(756);
      var5 = var1.getname("do_retval");
      var1.setlocal("do_rv", var5);
      var3 = null;
      var1.setline(758);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getval$46, (PyObject)null);
      var1.setlocal("_getval", var4);
      var3 = null;
      var1.setline(770);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_p$47, (PyObject)null);
      var1.setlocal("do_p", var4);
      var3 = null;
      var1.setline(776);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_pp$48, (PyObject)null);
      var1.setlocal("do_pp", var4);
      var3 = null;
      var1.setline(782);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_list$49, (PyObject)null);
      var1.setlocal("do_list", var4);
      var3 = null;
      var1.setline(826);
      var5 = var1.getname("do_list");
      var1.setlocal("do_l", var5);
      var3 = null;
      var1.setline(828);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_whatis$50, (PyObject)null);
      var1.setlocal("do_whatis", var4);
      var3 = null;
      var1.setline(855);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_alias$51, (PyObject)null);
      var1.setlocal("do_alias", var4);
      var3 = null;
      var1.setline(868);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_unalias$52, (PyObject)null);
      var1.setlocal("do_unalias", var4);
      var3 = null;
      var1.setline(875);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("do_continue"), PyString.fromInterned("do_step"), PyString.fromInterned("do_next"), PyString.fromInterned("do_return"), PyString.fromInterned("do_quit"), PyString.fromInterned("do_jump")});
      var1.setlocal("commands_resuming", var6);
      var3 = null;
      var1.setline(886);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_stack_trace$53, (PyObject)null);
      var1.setlocal("print_stack_trace", var4);
      var3 = null;
      var1.setline(893);
      var3 = new PyObject[]{var1.getname("line_prefix")};
      var4 = new PyFunction(var1.f_globals, var3, print_stack_entry$54, (PyObject)null);
      var1.setlocal("print_stack_entry", var4);
      var3 = null;
      var1.setline(905);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_help$55, (PyObject)null);
      var1.setlocal("help_help", var4);
      var3 = null;
      var1.setline(908);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_h$56, (PyObject)null);
      var1.setlocal("help_h", var4);
      var3 = null;
      var1.setline(915);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_where$57, (PyObject)null);
      var1.setlocal("help_where", var4);
      var3 = null;
      var1.setline(918);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_w$58, (PyObject)null);
      var1.setlocal("help_w", var4);
      var3 = null;
      var1.setline(924);
      var5 = var1.getname("help_w");
      var1.setlocal("help_bt", var5);
      var3 = null;
      var1.setline(926);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_down$59, (PyObject)null);
      var1.setlocal("help_down", var4);
      var3 = null;
      var1.setline(929);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_d$60, (PyObject)null);
      var1.setlocal("help_d", var4);
      var3 = null;
      var1.setline(934);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_up$61, (PyObject)null);
      var1.setlocal("help_up", var4);
      var3 = null;
      var1.setline(937);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_u$62, (PyObject)null);
      var1.setlocal("help_u", var4);
      var3 = null;
      var1.setline(942);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_break$63, (PyObject)null);
      var1.setlocal("help_break", var4);
      var3 = null;
      var1.setline(945);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_b$64, (PyObject)null);
      var1.setlocal("help_b", var4);
      var3 = null;
      var1.setline(958);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_clear$65, (PyObject)null);
      var1.setlocal("help_clear", var4);
      var3 = null;
      var1.setline(961);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_cl$66, (PyObject)null);
      var1.setlocal("help_cl", var4);
      var3 = null;
      var1.setline(974);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_tbreak$67, (PyObject)null);
      var1.setlocal("help_tbreak", var4);
      var3 = null;
      var1.setline(978);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_enable$68, (PyObject)null);
      var1.setlocal("help_enable", var4);
      var3 = null;
      var1.setline(983);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_disable$69, (PyObject)null);
      var1.setlocal("help_disable", var4);
      var3 = null;
      var1.setline(988);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_ignore$70, (PyObject)null);
      var1.setlocal("help_ignore", var4);
      var3 = null;
      var1.setline(996);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_condition$71, (PyObject)null);
      var1.setlocal("help_condition", var4);
      var3 = null;
      var1.setline(1003);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_step$72, (PyObject)null);
      var1.setlocal("help_step", var4);
      var3 = null;
      var1.setline(1006);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_s$73, (PyObject)null);
      var1.setlocal("help_s", var4);
      var3 = null;
      var1.setline(1011);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_until$74, (PyObject)null);
      var1.setlocal("help_until", var4);
      var3 = null;
      var1.setline(1014);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_unt$75, (PyObject)null);
      var1.setlocal("help_unt", var4);
      var3 = null;
      var1.setline(1019);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_next$76, (PyObject)null);
      var1.setlocal("help_next", var4);
      var3 = null;
      var1.setline(1022);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_n$77, (PyObject)null);
      var1.setlocal("help_n", var4);
      var3 = null;
      var1.setline(1027);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_return$78, (PyObject)null);
      var1.setlocal("help_return", var4);
      var3 = null;
      var1.setline(1030);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_r$79, (PyObject)null);
      var1.setlocal("help_r", var4);
      var3 = null;
      var1.setline(1034);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_continue$80, (PyObject)null);
      var1.setlocal("help_continue", var4);
      var3 = null;
      var1.setline(1037);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_cont$81, (PyObject)null);
      var1.setlocal("help_cont", var4);
      var3 = null;
      var1.setline(1040);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_c$82, (PyObject)null);
      var1.setlocal("help_c", var4);
      var3 = null;
      var1.setline(1044);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_jump$83, (PyObject)null);
      var1.setlocal("help_jump", var4);
      var3 = null;
      var1.setline(1047);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_j$84, (PyObject)null);
      var1.setlocal("help_j", var4);
      var3 = null;
      var1.setline(1051);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_debug$85, (PyObject)null);
      var1.setlocal("help_debug", var4);
      var3 = null;
      var1.setline(1057);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_list$86, (PyObject)null);
      var1.setlocal("help_list", var4);
      var3 = null;
      var1.setline(1060);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_l$87, (PyObject)null);
      var1.setlocal("help_l", var4);
      var3 = null;
      var1.setline(1069);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_args$88, (PyObject)null);
      var1.setlocal("help_args", var4);
      var3 = null;
      var1.setline(1072);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_a$89, (PyObject)null);
      var1.setlocal("help_a", var4);
      var3 = null;
      var1.setline(1076);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_p$90, (PyObject)null);
      var1.setlocal("help_p", var4);
      var3 = null;
      var1.setline(1080);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_pp$91, (PyObject)null);
      var1.setlocal("help_pp", var4);
      var3 = null;
      var1.setline(1084);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_exec$92, (PyObject)null);
      var1.setlocal("help_exec", var4);
      var3 = null;
      var1.setline(1095);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_run$93, (PyObject)null);
      var1.setlocal("help_run", var4);
      var3 = null;
      var1.setline(1102);
      var5 = var1.getname("help_run");
      var1.setlocal("help_restart", var5);
      var3 = null;
      var1.setline(1104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_quit$94, (PyObject)null);
      var1.setlocal("help_quit", var4);
      var3 = null;
      var1.setline(1107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_q$95, (PyObject)null);
      var1.setlocal("help_q", var4);
      var3 = null;
      var1.setline(1111);
      var5 = var1.getname("help_q");
      var1.setlocal("help_exit", var5);
      var3 = null;
      var1.setline(1113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_whatis$96, (PyObject)null);
      var1.setlocal("help_whatis", var4);
      var3 = null;
      var1.setline(1117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_EOF$97, (PyObject)null);
      var1.setlocal("help_EOF", var4);
      var3 = null;
      var1.setline(1121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_alias$98, (PyObject)null);
      var1.setlocal("help_alias", var4);
      var3 = null;
      var1.setline(1145);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_unalias$99, (PyObject)null);
      var1.setlocal("help_unalias", var4);
      var3 = null;
      var1.setline(1149);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_commands$100, (PyObject)null);
      var1.setlocal("help_commands", var4);
      var3 = null;
      var1.setline(1184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_pdb$101, (PyObject)null);
      var1.setlocal("help_pdb", var4);
      var3 = null;
      var1.setline(1187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lookupmodule$102, PyString.fromInterned("Helper function for break/clear parsing -- may be overridden.\n\n        lookupmodule() translates (possibly incomplete) file or module name\n        into an absolute file name.\n        "));
      var1.setlocal("lookupmodule", var4);
      var3 = null;
      var1.setline(1211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _runscript$103, (PyObject)null);
      var1.setlocal("_runscript", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var10000 = var1.getglobal("bdb").__getattr__("Bdb").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(4)};
      String[] var4 = new String[]{"skip"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(63);
      var1.getglobal("cmd").__getattr__("Cmd").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(64);
      PyInteger var9;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(65);
         var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"use_rawinput", var9);
         var3 = null;
      }

      var1.setline(66);
      PyString var10 = PyString.fromInterned("(Pdb) ");
      var1.getlocal(0).__setattr__((String)"prompt", var10);
      var3 = null;
      var1.setline(67);
      PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"aliases", var12);
      var3 = null;
      var1.setline(68);
      var10 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"mainpyfile", var10);
      var3 = null;
      var1.setline(69);
      var9 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_wait_for_mainpyfile", var9);
      var3 = null;

      PyException var13;
      PyObject var14;
      try {
         var1.setline(72);
         var14 = imp.importOne("readline", var1, -1);
         var1.setlocal(5, var14);
         var3 = null;
      } catch (Throwable var8) {
         var13 = Py.setException(var8, var1);
         if (!var13.match(var1.getglobal("ImportError"))) {
            throw var13;
         }

         var1.setline(74);
      }

      var1.setline(77);
      PyList var15 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"rcLines", var15);
      var3 = null;
      var1.setline(78);
      var10 = PyString.fromInterned("HOME");
      var10000 = var10._in(var1.getglobal("os").__getattr__("environ"));
      var3 = null;
      PyObject var5;
      PyObject var11;
      if (var10000.__nonzero__()) {
         label79: {
            var1.setline(79);
            var14 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME"));
            var1.setlocal(6, var14);
            var3 = null;

            try {
               var1.setline(81);
               var14 = var1.getglobal("open").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(".pdbrc")));
               var1.setlocal(7, var14);
               var3 = null;
            } catch (Throwable var7) {
               var13 = Py.setException(var7, var1);
               if (var13.match(var1.getglobal("IOError"))) {
                  var1.setline(83);
                  break label79;
               }

               throw var13;
            }

            var1.setline(85);
            var11 = var1.getlocal(7).__getattr__("readlines").__call__(var2).__iter__();

            while(true) {
               var1.setline(85);
               var5 = var11.__iternext__();
               if (var5 == null) {
                  var1.setline(87);
                  var1.getlocal(7).__getattr__("close").__call__(var2);
                  break;
               }

               var1.setlocal(8, var5);
               var1.setline(86);
               var1.getlocal(0).__getattr__("rcLines").__getattr__("append").__call__(var2, var1.getlocal(8));
            }
         }
      }

      label78: {
         try {
            var1.setline(89);
            var14 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".pdbrc"));
            var1.setlocal(7, var14);
            var3 = null;
         } catch (Throwable var6) {
            var13 = Py.setException(var6, var1);
            if (var13.match(var1.getglobal("IOError"))) {
               var1.setline(91);
               break label78;
            }

            throw var13;
         }

         var1.setline(93);
         var11 = var1.getlocal(7).__getattr__("readlines").__call__(var2).__iter__();

         while(true) {
            var1.setline(93);
            var5 = var11.__iternext__();
            if (var5 == null) {
               var1.setline(95);
               var1.getlocal(7).__getattr__("close").__call__(var2);
               break;
            }

            var1.setlocal(8, var5);
            var1.setline(94);
            var1.getlocal(0).__getattr__("rcLines").__getattr__("append").__call__(var2, var1.getlocal(8));
         }
      }

      var1.setline(97);
      var12 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commands", var12);
      var3 = null;
      var1.setline(98);
      var12 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commands_doprompt", var12);
      var3 = null;
      var1.setline(100);
      var12 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commands_silent", var12);
      var3 = null;
      var1.setline(102);
      var14 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("commands_defining", var14);
      var3 = null;
      var1.setline(104);
      var14 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("commands_bnum", var14);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$5(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      var1.getglobal("bdb").__getattr__("Bdb").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(109);
      var1.getlocal(0).__getattr__("forget").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject forget$6(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(113);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var4);
      var3 = null;
      var1.setline(114);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"curindex", var5);
      var3 = null;
      var1.setline(115);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("curframe", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup$7(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      var1.getlocal(0).__getattr__("forget").__call__(var2);
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__getattr__("get_stack").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("stack", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("curindex", var5);
      var5 = null;
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("curframe", var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_locals");
      var1.getlocal(0).__setattr__("curframe_locals", var3);
      var3 = null;
      var1.setline(125);
      var1.getlocal(0).__getattr__("execRcLines").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execRcLines$8(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      if (var1.getlocal(0).__getattr__("rcLines").__nonzero__()) {
         var1.setline(131);
         PyObject var3 = var1.getlocal(0).__getattr__("rcLines");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(133);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"rcLines", var6);
         var3 = null;
         var1.setline(134);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(134);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(135);
            PyObject var5 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(136);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            PyObject var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               var10000 = var5._ne(PyString.fromInterned("#"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(137);
               var1.getlocal(0).__getattr__("onecmd").__call__(var2, var1.getlocal(2));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_call$9(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("This method is called when there is the remote possibility\n        that we ever need to stop in this function.");
      var1.setline(144);
      if (var1.getlocal(0).__getattr__("_wait_for_mainpyfile").__nonzero__()) {
         var1.setline(145);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(146);
         if (var1.getlocal(0).__getattr__("stop_here").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(147);
            PyObject var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, PyString.fromInterned("--Call--"));
            var1.setline(148);
            var1.getlocal(0).__getattr__("interaction").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject user_line$10(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyString.fromInterned("This function is called when we stop or break at this line.");
      var1.setline(152);
      if (var1.getlocal(0).__getattr__("_wait_for_mainpyfile").__nonzero__()) {
         var1.setline(153);
         PyObject var3 = var1.getlocal(0).__getattr__("mainpyfile");
         PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("f_lineno");
            var10000 = var3._le(Py.newInteger(0));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(155);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(156);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_wait_for_mainpyfile", var4);
         var3 = null;
      }

      var1.setline(157);
      if (var1.getlocal(0).__getattr__("bp_commands").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(158);
         var1.getlocal(0).__getattr__("interaction").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bp_commands$11(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyString.fromInterned("Call every command that was set for the current active breakpoint\n        (if there is one).\n\n        Returns True if the normal interaction function must be called,\n        False otherwise.");
      var1.setline(167);
      PyObject var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("currentbp"), (PyObject)var1.getglobal("False"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("currentbp");
         var10000 = var3._in(var1.getlocal(0).__getattr__("commands"));
         var3 = null;
      }

      PyInteger var5;
      if (!var10000.__nonzero__()) {
         var1.setline(182);
         var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(169);
         var3 = var1.getlocal(0).__getattr__("currentbp");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(170);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"currentbp", var5);
         var3 = null;
         var1.setline(171);
         var3 = var1.getlocal(0).__getattr__("lastcmd");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(172);
         var1.getlocal(0).__getattr__("setup").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
         var1.setline(173);
         var3 = var1.getlocal(0).__getattr__("commands").__getitem__(var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(173);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(175);
               var3 = var1.getlocal(3);
               var1.getlocal(0).__setattr__("lastcmd", var3);
               var3 = null;
               var1.setline(176);
               if (var1.getlocal(0).__getattr__("commands_silent").__getitem__(var1.getlocal(2)).__not__().__nonzero__()) {
                  var1.setline(177);
                  var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")));
               }

               var1.setline(178);
               if (var1.getlocal(0).__getattr__("commands_doprompt").__getitem__(var1.getlocal(2)).__nonzero__()) {
                  var1.setline(179);
                  var1.getlocal(0).__getattr__("cmdloop").__call__(var2);
               }

               var1.setline(180);
               var1.getlocal(0).__getattr__("forget").__call__(var2);
               var1.setline(181);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(174);
            var1.getlocal(0).__getattr__("onecmd").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject user_return$12(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyString.fromInterned("This function is called when a return trap is set here.");
      var1.setline(186);
      if (var1.getlocal(0).__getattr__("_wait_for_mainpyfile").__nonzero__()) {
         var1.setline(187);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(188);
         PyObject var3 = var1.getlocal(2);
         var1.getlocal(1).__getattr__("f_locals").__setitem__((PyObject)PyString.fromInterned("__return__"), var3);
         var3 = null;
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, PyString.fromInterned("--Return--"));
         var1.setline(190);
         var1.getlocal(0).__getattr__("interaction").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject user_exception$13(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyString.fromInterned("This function is called if an exception occurs,\n        but only if we are to stop at or just below this level.");
      var1.setline(195);
      if (var1.getlocal(0).__getattr__("_wait_for_mainpyfile").__nonzero__()) {
         var1.setline(196);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(197);
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
         var1.setline(198);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.getlocal(1).__getattr__("f_locals").__setitem__((PyObject)PyString.fromInterned("__exception__"), var6);
         var3 = null;
         var1.setline(199);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(200);
            var3 = var1.getlocal(3);
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(201);
            var3 = var1.getlocal(3).__getattr__("__name__");
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(202);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var3, var1.getlocal(6)._add(PyString.fromInterned(":")));
         Py.println(var3, var1.getglobal("_saferepr").__call__(var2, var1.getlocal(4)));
         var1.setline(203);
         var1.getlocal(0).__getattr__("interaction").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject interaction$14(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      var1.getlocal(0).__getattr__("setup").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(209);
      var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")));
      var1.setline(210);
      var1.getlocal(0).__getattr__("cmdloop").__call__(var2);
      var1.setline(211);
      var1.getlocal(0).__getattr__("forget").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject displayhook$15(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("Custom displayhook for the exec in default(), which prevents\n        assignment of the _ variable in the builtins.\n        ");
      var1.setline(218);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(219);
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default$16(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("!"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(222);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(223);
      var3 = var1.getlocal(0).__getattr__("curframe_locals");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(224);
      var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(226);
         var3 = var1.getname("compile").__call__((ThreadState)var2, var1.getlocal(1)._add(PyString.fromInterned("\n")), (PyObject)PyString.fromInterned("<stdin>"), (PyObject)PyString.fromInterned("single"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(227);
         var3 = var1.getname("sys").__getattr__("stdout");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(228);
         var3 = var1.getname("sys").__getattr__("stdin");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(229);
         var3 = var1.getname("sys").__getattr__("displayhook");
         var1.setlocal(7, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(231);
            var4 = var1.getlocal(0).__getattr__("stdin");
            var1.getname("sys").__setattr__("stdin", var4);
            var4 = null;
            var1.setline(232);
            var4 = var1.getlocal(0).__getattr__("stdout");
            var1.getname("sys").__setattr__("stdout", var4);
            var4 = null;
            var1.setline(233);
            var4 = var1.getlocal(0).__getattr__("displayhook");
            var1.getname("sys").__setattr__("displayhook", var4);
            var4 = null;
            var1.setline(234);
            Py.exec(var1.getlocal(4), var1.getlocal(3), var1.getlocal(2));
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(236);
            var4 = var1.getlocal(5);
            var1.getname("sys").__setattr__("stdout", var4);
            var4 = null;
            var1.setline(237);
            var4 = var1.getlocal(6);
            var1.getname("sys").__setattr__("stdin", var4);
            var4 = null;
            var1.setline(238);
            var4 = var1.getlocal(7);
            var1.getname("sys").__setattr__("displayhook", var4);
            var4 = null;
            throw (Throwable)var7;
         }

         var1.setline(236);
         var4 = var1.getlocal(5);
         var1.getname("sys").__setattr__("stdout", var4);
         var4 = null;
         var1.setline(237);
         var4 = var1.getlocal(6);
         var1.getname("sys").__setattr__("stdin", var4);
         var4 = null;
         var1.setline(238);
         var4 = var1.getlocal(7);
         var1.getname("sys").__setattr__("displayhook", var4);
         var4 = null;
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(240);
         var4 = var1.getname("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(9, var6);
         var6 = null;
         var4 = null;
         var1.setline(241);
         var4 = var1.getname("type").__call__(var2, var1.getlocal(8));
         var10000 = var4._eq(var1.getname("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(242);
            var4 = var1.getlocal(8);
            var1.setlocal(10, var4);
            var4 = null;
         } else {
            var1.setline(243);
            var4 = var1.getlocal(8).__getattr__("__name__");
            var1.setlocal(10, var4);
            var4 = null;
         }

         var1.setline(244);
         var4 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var4, PyString.fromInterned("***"));
         Py.printComma(var4, var1.getlocal(10)._add(PyString.fromInterned(":")));
         Py.println(var4, var1.getlocal(9));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject precmd$17(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyString.fromInterned("Handle alias expansion and ';;' separator.");
      var1.setline(248);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("strip").__call__(var2).__not__().__nonzero__()) {
         var1.setline(249);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(250);
         PyObject var4 = var1.getlocal(1).__getattr__("split").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;

         while(true) {
            var1.setline(251);
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("aliases"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(262);
               var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               var10000 = var4._ne(PyString.fromInterned("alias"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(263);
                  var4 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";;"));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(264);
                  var4 = var1.getlocal(5);
                  var10000 = var4._ge(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(266);
                     var4 = var1.getlocal(1).__getslice__(var1.getlocal(5)._add(Py.newInteger(2)), (PyObject)null, (PyObject)null).__getattr__("lstrip").__call__(var2);
                     var1.setlocal(6, var4);
                     var4 = null;
                     var1.setline(267);
                     var1.getlocal(0).__getattr__("cmdqueue").__getattr__("append").__call__(var2, var1.getlocal(6));
                     var1.setline(268);
                     var4 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null).__getattr__("rstrip").__call__(var2);
                     var1.setlocal(1, var4);
                     var4 = null;
                  }
               }

               var1.setline(269);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(252);
            var4 = var1.getlocal(0).__getattr__("aliases").__getitem__(var1.getlocal(2).__getitem__(Py.newInteger(0)));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(253);
            PyInteger var7 = Py.newInteger(1);
            var1.setlocal(3, var7);
            var4 = null;
            var1.setline(254);
            var4 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(254);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(258);
                  var4 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%*"), (PyObject)PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
                  var1.setlocal(1, var4);
                  var4 = null;
                  var1.setline(259);
                  var4 = var1.getlocal(1).__getattr__("split").__call__(var2);
                  var1.setlocal(2, var4);
                  var4 = null;
                  break;
               }

               var1.setlocal(4, var5);
               var1.setline(255);
               PyObject var6 = var1.getlocal(1).__getattr__("replace").__call__(var2, PyString.fromInterned("%")._add(var1.getglobal("str").__call__(var2, var1.getlocal(3))), var1.getlocal(4));
               var1.setlocal(1, var6);
               var6 = null;
               var1.setline(257);
               var6 = var1.getlocal(3)._add(Py.newInteger(1));
               var1.setlocal(3, var6);
               var6 = null;
            }
         }
      }
   }

   public PyObject onecmd$18(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Interpret the argument as though it had been typed in response\n        to the prompt.\n\n        Checks whether this line is typed at the normal prompt or in\n        a breakpoint command list definition.\n        ");
      var1.setline(278);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("commands_defining").__not__().__nonzero__()) {
         var1.setline(279);
         var3 = var1.getglobal("cmd").__getattr__("Cmd").__getattr__("onecmd").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("handle_command_def").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject handle_command_def$19(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyString.fromInterned("Handles one command line during command list definition.");
      var1.setline(285);
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
      var1.setline(286);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(287);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(288);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._eq(PyString.fromInterned("silent"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(289);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__getattr__("commands_silent").__setitem__(var1.getlocal(0).__getattr__("commands_bnum"), var3);
            var3 = null;
            var1.setline(290);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(291);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("end"));
            var3 = null;
            PyInteger var9;
            if (var10000.__nonzero__()) {
               var1.setline(292);
               PyList var10 = new PyList(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"cmdqueue", var10);
               var3 = null;
               var1.setline(293);
               var9 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var9;
            } else {
               var1.setline(294);
               PyObject var7 = var1.getlocal(0).__getattr__("commands").__getitem__(var1.getlocal(0).__getattr__("commands_bnum"));
               var1.setlocal(4, var7);
               var4 = null;
               var1.setline(295);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(296);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2)._add(PyString.fromInterned(" "))._add(var1.getlocal(3)));
               } else {
                  var1.setline(298);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
               }

               try {
                  var1.setline(301);
                  var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("do_")._add(var1.getlocal(2)));
                  var1.setlocal(5, var7);
                  var4 = null;
               } catch (Throwable var6) {
                  PyException var8 = Py.setException(var6, var1);
                  if (!var8.match(var1.getglobal("AttributeError"))) {
                     throw var8;
                  }

                  var1.setline(303);
                  var5 = var1.getlocal(0).__getattr__("default");
                  var1.setlocal(5, var5);
                  var5 = null;
               }

               var1.setline(305);
               var7 = var1.getlocal(5).__getattr__("func_name");
               var10000 = var7._in(var1.getlocal(0).__getattr__("commands_resuming"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(306);
                  var7 = var1.getglobal("False");
                  var1.getlocal(0).__getattr__("commands_doprompt").__setitem__(var1.getlocal(0).__getattr__("commands_bnum"), var7);
                  var4 = null;
                  var1.setline(307);
                  PyList var11 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"cmdqueue", var11);
                  var4 = null;
                  var1.setline(308);
                  var9 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var9;
               } else {
                  var1.setline(309);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject do_commands$20(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyString.fromInterned("Defines a list of commands associated to a breakpoint.\n\n        Those commands will be executed whenever the breakpoint causes\n        the program to stop execution.");
      var1.setline(322);
      PyObject var3;
      PyObject var4;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(323);
         var3 = var1.getglobal("len").__call__(var2, var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber"))._sub(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         try {
            var1.setline(326);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(328);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("Usage : commands [bnum]\n        ...\n        end"));
            var1.setline(330);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(331);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("commands_bnum", var3);
      var3 = null;
      var1.setline(332);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__getattr__("commands").__setitem__((PyObject)var1.getlocal(2), var7);
      var3 = null;
      var1.setline(333);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__getattr__("commands_doprompt").__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.setline(334);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__getattr__("commands_silent").__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.setline(335);
      var3 = var1.getlocal(0).__getattr__("prompt");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(336);
      PyString var8 = PyString.fromInterned("(com) ");
      var1.getlocal(0).__setattr__((String)"prompt", var8);
      var3 = null;
      var1.setline(337);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("commands_defining", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(339);
         var1.getlocal(0).__getattr__("cmdloop").__call__(var2);
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(341);
         var4 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("commands_defining", var4);
         var4 = null;
         var1.setline(342);
         var4 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("prompt", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(341);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("commands_defining", var4);
      var4 = null;
      var1.setline(342);
      var4 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("prompt", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_break$21(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyObject var3;
      PyObject var4;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(347);
         if (var1.getlocal(0).__getattr__("breaks").__nonzero__()) {
            var1.setline(348);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, PyString.fromInterned("Num Type         Disp Enb   Where"));
            var1.setline(349);
            var3 = var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber").__iter__();

            while(true) {
               var1.setline(349);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(3, var4);
               var1.setline(350);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(351);
                  var1.getlocal(3).__getattr__("bpprint").__call__(var2, var1.getlocal(0).__getattr__("stdout"));
               }
            }
         }

         var1.setline(352);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(355);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(356);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(357);
         var3 = var1.getglobal("None");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(358);
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(359);
         var3 = var1.getlocal(7);
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(361);
            var3 = var1.getlocal(1).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("lstrip").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(362);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null).__getattr__("rstrip").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(364);
         var3 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(365);
         var3 = var1.getglobal("None");
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(366);
         var3 = var1.getlocal(8);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         PyException var12;
         if (var10000.__nonzero__()) {
            var1.setline(367);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null).__getattr__("rstrip").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(368);
            var3 = var1.getlocal(0).__getattr__("lookupmodule").__call__(var2, var1.getlocal(4));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(369);
            if (var1.getlocal(10).__not__().__nonzero__()) {
               var1.setline(370);
               var3 = var1.getlocal(0).__getattr__("stdout");
               Py.printComma(var3, PyString.fromInterned("*** "));
               Py.printComma(var3, var1.getglobal("repr").__call__(var2, var1.getlocal(4)));
               var1.setline(371);
               var3 = var1.getlocal(0).__getattr__("stdout");
               Py.println(var3, PyString.fromInterned("not found from sys.path"));
               var1.setline(372);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(374);
            var3 = var1.getlocal(10);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(375);
            var3 = var1.getlocal(1).__getslice__(var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("lstrip").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;

            try {
               var1.setline(377);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var3);
               var3 = null;
            } catch (Throwable var9) {
               var12 = Py.setException(var9, var1);
               if (var12.match(var1.getglobal("ValueError"))) {
                  var4 = var12.value;
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(379);
                  var4 = var1.getlocal(0).__getattr__("stdout");
                  Py.printComma(var4, PyString.fromInterned("*** Bad lineno:"));
                  Py.println(var4, var1.getlocal(1));
                  var1.setline(380);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               throw var12;
            }
         } else {
            try {
               var1.setline(384);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var3);
               var3 = null;
            } catch (Throwable var11) {
               var12 = Py.setException(var11, var1);
               if (!var12.match(var1.getglobal("ValueError"))) {
                  throw var12;
               }

               PyObject var5;
               try {
                  var1.setline(387);
                  var4 = var1.getglobal("eval").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals"), var1.getlocal(0).__getattr__("curframe_locals"));
                  var1.setlocal(12, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  Py.setException(var8, var1);
                  var1.setline(391);
                  var5 = var1.getlocal(1);
                  var1.setlocal(12, var5);
                  var5 = null;
               }

               try {
                  var1.setline(393);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(12), (PyObject)PyString.fromInterned("im_func")).__nonzero__()) {
                     var1.setline(394);
                     var4 = var1.getlocal(12).__getattr__("im_func");
                     var1.setlocal(12, var4);
                     var4 = null;
                  }

                  var1.setline(395);
                  var4 = var1.getlocal(12).__getattr__("func_code");
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(398);
                  var4 = var1.getlocal(13).__getattr__("co_name");
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(399);
                  var4 = var1.getlocal(13).__getattr__("co_firstlineno");
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(400);
                  var4 = var1.getlocal(13).__getattr__("co_filename");
                  var1.setlocal(4, var4);
                  var4 = null;
               } catch (Throwable var10) {
                  Py.setException(var10, var1);
                  var1.setline(403);
                  var5 = var1.getlocal(0).__getattr__("lineinfo").__call__(var2, var1.getlocal(1));
                  PyObject[] var6 = Py.unpackSequence(var5, 3);
                  PyObject var7 = var6[0];
                  var1.setlocal(14, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var7 = var6[2];
                  var1.setlocal(15, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(404);
                  if (var1.getlocal(14).__not__().__nonzero__()) {
                     var1.setline(405);
                     var5 = var1.getlocal(0).__getattr__("stdout");
                     Py.printComma(var5, PyString.fromInterned("*** The specified object"));
                     var1.setline(406);
                     var5 = var1.getlocal(0).__getattr__("stdout");
                     Py.printComma(var5, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
                     var1.setline(407);
                     var5 = var1.getlocal(0).__getattr__("stdout");
                     Py.println(var5, PyString.fromInterned("is not a function"));
                     var1.setline(408);
                     var5 = var1.getlocal(0).__getattr__("stdout");
                     Py.println(var5, PyString.fromInterned("or was not found along sys.path."));
                     var1.setline(409);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(410);
                  var5 = var1.getlocal(14);
                  var1.setlocal(9, var5);
                  var5 = null;
                  var1.setline(411);
                  var5 = var1.getglobal("int").__call__(var2, var1.getlocal(15));
                  var1.setlocal(5, var5);
                  var5 = null;
               }
            }
         }

         var1.setline(412);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(413);
            var3 = var1.getlocal(0).__getattr__("defaultFile").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(415);
         var3 = var1.getlocal(0).__getattr__("checkline").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         var1.setlocal(16, var3);
         var3 = null;
         var1.setline(416);
         if (var1.getlocal(16).__nonzero__()) {
            var1.setline(418);
            var10000 = var1.getlocal(0).__getattr__("set_break");
            PyObject[] var13 = new PyObject[]{var1.getlocal(4), var1.getlocal(16), var1.getlocal(2), var1.getlocal(6), var1.getlocal(9)};
            var3 = var10000.__call__(var2, var13);
            var1.setlocal(17, var3);
            var3 = null;
            var1.setline(419);
            if (var1.getlocal(17).__nonzero__()) {
               var1.setline(419);
               var3 = var1.getlocal(0).__getattr__("stdout");
               Py.printComma(var3, PyString.fromInterned("***"));
               Py.println(var3, var1.getlocal(17));
            } else {
               var1.setline(421);
               var3 = var1.getlocal(0).__getattr__("get_breaks").__call__(var2, var1.getlocal(4), var1.getlocal(16)).__getitem__(Py.newInteger(-1));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(422);
               var3 = var1.getlocal(0).__getattr__("stdout");
               Py.println(var3, PyString.fromInterned("Breakpoint %d at %s:%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("number"), var1.getlocal(3).__getattr__("file"), var1.getlocal(3).__getattr__("line")})));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject defaultFile$22(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyString.fromInterned("Produce a reasonable default.");
      var1.setline(429);
      PyObject var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_code").__getattr__("co_filename");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(430);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("<string>"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("mainpyfile");
      }

      if (var10000.__nonzero__()) {
         var1.setline(431);
         var3 = var1.getlocal(0).__getattr__("mainpyfile");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(432);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_tbreak$23(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      var1.getlocal(0).__getattr__("do_break").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject lineinfo$24(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(442);
      PyObject var5 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(443);
      var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var5._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(445);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
      } else {
         var1.setline(446);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var5._eq(Py.newInteger(3));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(450);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(448);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(1)).__getattr__("strip").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(451);
      PyObject var4 = var1.getlocal(4);
      var10000 = var4._eq(PyString.fromInterned(""));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(451);
         var5 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(452);
         var4 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(454);
         var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var4._eq(PyString.fromInterned("self"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(455);
            var1.getlocal(5).__delitem__((PyObject)Py.newInteger(0));
            var1.setline(456);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(457);
               var5 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var5;
            }
         }

         var1.setline(459);
         var4 = var1.getlocal(0).__getattr__("defaultFile").__call__(var2);
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(460);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(461);
            var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
            var1.setlocal(7, var4);
            var4 = null;
         } else {
            var1.setline(465);
            var4 = var1.getlocal(0).__getattr__("lookupmodule").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(0)));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(466);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(467);
               var4 = var1.getlocal(8);
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(468);
            var4 = var1.getlocal(5).__getitem__(Py.newInteger(1));
            var1.setlocal(7, var4);
            var4 = null;
         }

         var1.setline(469);
         var4 = var1.getglobal("find_function").__call__(var2, var1.getlocal(7), var1.getlocal(6));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(470);
         var10000 = var1.getlocal(9);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         var5 = var10000;
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject checkline$25(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyString.fromInterned("Check whether specified line seems to be executable.\n\n        Return `lineno` if it is, 0 if not (e.g. a docstring, comment, blank\n        line or EOF). Warning: testing is not comprehensive.\n        ");
      var1.setline(480);
      var1.setline(480);
      PyObject var3 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("curframe")).__nonzero__() ? var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals") : var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(481);
      var3 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(482);
      PyInteger var5;
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(483);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, PyString.fromInterned("End of file"));
         var1.setline(484);
         var5 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(485);
         PyObject var4 = var1.getlocal(4).__getattr__("strip").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(487);
         PyObject var10000 = var1.getlocal(4).__not__();
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(PyString.fromInterned("#"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("\"\"\""));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("'''"));
                  var4 = null;
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(489);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("*** Blank or comment"));
            var1.setline(490);
            var5 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(491);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject do_enable$26(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(495);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(495);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);

         PyException var5;
         PyObject var6;
         PyObject var8;
         try {
            var1.setline(497);
            var8 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var8);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               var1.setline(499);
               var6 = var1.getlocal(0).__getattr__("stdout");
               Py.println(var6, PyString.fromInterned("Breakpoint index %r is not a number")._mod(var1.getlocal(3)));
               continue;
            }

            throw var5;
         }

         var1.setline(502);
         PyInteger var9 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(3);
         PyInteger var10000 = var9;
         var8 = var10001;
         if ((var6 = var10000._le(var10001)).__nonzero__()) {
            var6 = var8._lt(var1.getglobal("len").__call__(var2, var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber")));
         }

         var5 = null;
         if (var6.__not__().__nonzero__()) {
            var1.setline(503);
            var8 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var8, PyString.fromInterned("No breakpoint numbered"));
            Py.println(var8, var1.getlocal(3));
         } else {
            var1.setline(506);
            var8 = var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber").__getitem__(var1.getlocal(3));
            var1.setlocal(4, var8);
            var5 = null;
            var1.setline(507);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(508);
               var1.getlocal(4).__getattr__("enable").__call__(var2);
            }
         }
      }
   }

   public PyObject do_disable$27(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(512);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(512);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);

         PyException var5;
         PyObject var6;
         PyObject var8;
         try {
            var1.setline(514);
            var8 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var8);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               var1.setline(516);
               var6 = var1.getlocal(0).__getattr__("stdout");
               Py.println(var6, PyString.fromInterned("Breakpoint index %r is not a number")._mod(var1.getlocal(3)));
               continue;
            }

            throw var5;
         }

         var1.setline(519);
         PyInteger var9 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(3);
         PyInteger var10000 = var9;
         var8 = var10001;
         if ((var6 = var10000._le(var10001)).__nonzero__()) {
            var6 = var8._lt(var1.getglobal("len").__call__(var2, var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber")));
         }

         var5 = null;
         if (var6.__not__().__nonzero__()) {
            var1.setline(520);
            var8 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var8, PyString.fromInterned("No breakpoint numbered"));
            Py.println(var8, var1.getlocal(3));
         } else {
            var1.setline(523);
            var8 = var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber").__getitem__(var1.getlocal(3));
            var1.setlocal(4, var8);
            var5 = null;
            var1.setline(524);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(525);
               var1.getlocal(4).__getattr__("disable").__call__(var2);
            }
         }
      }
   }

   public PyObject do_condition$28(PyFrame var1, ThreadState var2) {
      var1.setline(529);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      PyException var8;
      try {
         var1.setline(531);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("ValueError"))) {
            var1.setline(534);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("Breakpoint index %r is not a number")._mod(var1.getlocal(2).__getitem__(Py.newInteger(0))));
            var1.setline(536);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      try {
         var1.setline(538);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(540);
         var4 = var1.getglobal("None");
         var1.setlocal(4, var4);
         var4 = null;
      }

      try {
         var1.setline(542);
         var3 = var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber").__getitem__(var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var7) {
         var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("IndexError"))) {
            var1.setline(544);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("Breakpoint index %r is not valid")._mod(var1.getlocal(2).__getitem__(Py.newInteger(0))));
            var1.setline(545);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      var1.setline(546);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(547);
         var3 = var1.getlocal(4);
         var1.getlocal(5).__setattr__("cond", var3);
         var3 = null;
         var1.setline(548);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(549);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var3, PyString.fromInterned("Breakpoint"));
            Py.printComma(var3, var1.getlocal(3));
            var1.setline(550);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, PyString.fromInterned("is now unconditional."));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_ignore$29(PyFrame var1, ThreadState var2) {
      var1.setline(553);
      PyString.fromInterned("arg is bp number followed by ignore count.");
      var1.setline(554);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      PyException var8;
      try {
         var1.setline(556);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("ValueError"))) {
            var1.setline(559);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("Breakpoint index %r is not a number")._mod(var1.getlocal(2).__getitem__(Py.newInteger(0))));
            var1.setline(561);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      try {
         var1.setline(563);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)).__getattr__("strip").__call__(var2));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(565);
         PyInteger var9 = Py.newInteger(0);
         var1.setlocal(4, var9);
         var4 = null;
      }

      try {
         var1.setline(567);
         var3 = var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber").__getitem__(var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var7) {
         var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("IndexError"))) {
            var1.setline(569);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var4, PyString.fromInterned("Breakpoint index %r is not valid")._mod(var1.getlocal(2).__getitem__(Py.newInteger(0))));
            var1.setline(570);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      var1.setline(571);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(572);
         var3 = var1.getlocal(4);
         var1.getlocal(5).__setattr__("ignore", var3);
         var3 = null;
         var1.setline(573);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(574);
            PyString var10 = PyString.fromInterned("Will ignore next ");
            var1.setlocal(6, var10);
            var3 = null;
            var1.setline(575);
            var3 = var1.getlocal(4);
            var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(576);
               var3 = var1.getlocal(6)._add(PyString.fromInterned("%d crossings")._mod(var1.getlocal(4)));
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(578);
               var3 = var1.getlocal(6)._add(PyString.fromInterned("1 crossing"));
               var1.setlocal(6, var3);
               var3 = null;
            }

            var1.setline(579);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, var1.getlocal(6)._add(PyString.fromInterned(" of breakpoint %d.")._mod(var1.getlocal(3))));
         } else {
            var1.setline(581);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var3, PyString.fromInterned("Will stop next time breakpoint"));
            var1.setline(582);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var3, var1.getlocal(3));
            Py.println(var3, PyString.fromInterned("is reached."));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_clear$30(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString.fromInterned("Three possibilities, tried in this order:\n        clear -> clear all breaks, ask for confirmation\n        clear file:lineno -> clear all breaks at file:lineno\n        clear bpno bpno ... -> clear breakpoints by number");
      var1.setline(589);
      PyString var3;
      PyObject var4;
      PyObject var10;
      PyException var14;
      PyObject var10000;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         try {
            var1.setline(591);
            var10 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Clear all breaks? "));
            var1.setlocal(2, var10);
            var3 = null;
         } catch (Throwable var7) {
            var14 = Py.setException(var7, var1);
            if (!var14.match(var1.getglobal("EOFError"))) {
               throw var14;
            }

            var1.setline(593);
            PyString var12 = PyString.fromInterned("no");
            var1.setlocal(2, var12);
            var4 = null;
         }

         var1.setline(594);
         var10 = var1.getlocal(2).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
         var1.setlocal(2, var10);
         var3 = null;
         var1.setline(595);
         var10 = var1.getlocal(2);
         var10000 = var10._in(new PyTuple(new PyObject[]{PyString.fromInterned("y"), PyString.fromInterned("yes")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(596);
            var1.getlocal(0).__getattr__("clear_all_breaks").__call__(var2);
         }

         var1.setline(597);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(598);
         var3 = PyString.fromInterned(":");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(600);
            var10 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(3, var10);
            var3 = null;
            var1.setline(601);
            var10 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.setlocal(4, var10);
            var3 = null;
            var1.setline(602);
            var10 = var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var10);
            var3 = null;

            label65: {
               try {
                  var1.setline(604);
                  var10 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
                  var1.setlocal(5, var10);
                  var3 = null;
               } catch (Throwable var8) {
                  var14 = Py.setException(var8, var1);
                  if (var14.match(var1.getglobal("ValueError"))) {
                     var1.setline(606);
                     var4 = PyString.fromInterned("Invalid line number (%s)")._mod(var1.getlocal(1));
                     var1.setlocal(6, var4);
                     var4 = null;
                     break label65;
                  }

                  throw var14;
               }

               var1.setline(608);
               var4 = var1.getlocal(0).__getattr__("clear_break").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(609);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(609);
               var10 = var1.getlocal(0).__getattr__("stdout");
               Py.printComma(var10, PyString.fromInterned("***"));
               Py.println(var10, var1.getlocal(6));
            }

            var1.setline(610);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(611);
            var10 = var1.getlocal(1).__getattr__("split").__call__(var2);
            var1.setlocal(7, var10);
            var3 = null;
            var1.setline(612);
            var10 = var1.getlocal(7).__iter__();

            while(true) {
               var1.setline(612);
               var4 = var10.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);

               PyException var5;
               PyObject var6;
               PyObject var11;
               try {
                  var1.setline(614);
                  var11 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
                  var1.setlocal(3, var11);
                  var5 = null;
               } catch (Throwable var9) {
                  var5 = Py.setException(var9, var1);
                  if (var5.match(var1.getglobal("ValueError"))) {
                     var1.setline(616);
                     var6 = var1.getlocal(0).__getattr__("stdout");
                     Py.println(var6, PyString.fromInterned("Breakpoint index %r is not a number")._mod(var1.getlocal(3)));
                     continue;
                  }

                  throw var5;
               }

               var1.setline(619);
               PyInteger var13 = Py.newInteger(0);
               PyObject var10001 = var1.getlocal(3);
               PyInteger var15 = var13;
               var11 = var10001;
               if ((var6 = var15._le(var10001)).__nonzero__()) {
                  var6 = var11._lt(var1.getglobal("len").__call__(var2, var1.getglobal("bdb").__getattr__("Breakpoint").__getattr__("bpbynumber")));
               }

               var5 = null;
               if (var6.__not__().__nonzero__()) {
                  var1.setline(620);
                  var11 = var1.getlocal(0).__getattr__("stdout");
                  Py.printComma(var11, PyString.fromInterned("No breakpoint numbered"));
                  Py.println(var11, var1.getlocal(3));
               } else {
                  var1.setline(622);
                  var11 = var1.getlocal(0).__getattr__("clear_bpbynumber").__call__(var2, var1.getlocal(3));
                  var1.setlocal(6, var11);
                  var5 = null;
                  var1.setline(623);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(624);
                     var11 = var1.getlocal(0).__getattr__("stdout");
                     Py.printComma(var11, PyString.fromInterned("***"));
                     Py.println(var11, var1.getlocal(6));
                  } else {
                     var1.setline(626);
                     var11 = var1.getlocal(0).__getattr__("stdout");
                     Py.printComma(var11, PyString.fromInterned("Deleted breakpoint"));
                     Py.println(var11, var1.getlocal(3));
                  }
               }
            }
         }
      }
   }

   public PyObject do_where$31(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      var1.getlocal(0).__getattr__("print_stack_trace").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_up$32(PyFrame var1, ThreadState var2) {
      var1.setline(635);
      PyObject var3 = var1.getlocal(0).__getattr__("curindex");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(636);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, PyString.fromInterned("*** Oldest frame"));
      } else {
         var1.setline(638);
         var3 = var1.getlocal(0).__getattr__("curindex")._sub(Py.newInteger(1));
         var1.getlocal(0).__setattr__("curindex", var3);
         var3 = null;
         var1.setline(639);
         var3 = var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("curframe", var3);
         var3 = null;
         var1.setline(640);
         var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_locals");
         var1.getlocal(0).__setattr__("curframe_locals", var3);
         var3 = null;
         var1.setline(641);
         var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")));
         var1.setline(642);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("lineno", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_down$33(PyFrame var1, ThreadState var2) {
      var1.setline(646);
      PyObject var3 = var1.getlocal(0).__getattr__("curindex")._add(Py.newInteger(1));
      PyObject var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(647);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, PyString.fromInterned("*** Newest frame"));
      } else {
         var1.setline(649);
         var3 = var1.getlocal(0).__getattr__("curindex")._add(Py.newInteger(1));
         var1.getlocal(0).__setattr__("curindex", var3);
         var3 = null;
         var1.setline(650);
         var3 = var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("curframe", var3);
         var3 = null;
         var1.setline(651);
         var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_locals");
         var1.getlocal(0).__setattr__("curframe_locals", var3);
         var3 = null;
         var1.setline(652);
         var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")));
         var1.setline(653);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("lineno", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_until$34(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      var1.getlocal(0).__getattr__("set_until").__call__(var2, var1.getlocal(0).__getattr__("curframe"));
      var1.setline(658);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_step$35(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      var1.getlocal(0).__getattr__("set_step").__call__(var2);
      var1.setline(663);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_next$36(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      var1.getlocal(0).__getattr__("set_next").__call__(var2, var1.getlocal(0).__getattr__("curframe"));
      var1.setline(668);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_run$37(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyString.fromInterned("Restart program by raising an exception to be caught in the main\n        debugger loop.  If arguments were given, set them in sys.argv.");
      var1.setline(674);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(675);
         PyObject var3 = imp.importOne("shlex", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(676);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(677);
         var3 = var1.getlocal(2).__getattr__("split").__call__(var2, var1.getlocal(1));
         var1.getglobal("sys").__setattr__("argv", var3);
         var3 = null;
         var1.setline(678);
         var3 = var1.getlocal(3);
         var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
         var3 = null;
      }

      var1.setline(679);
      throw Py.makeException(var1.getglobal("Restart"));
   }

   public PyObject do_return$38(PyFrame var1, ThreadState var2) {
      var1.setline(684);
      var1.getlocal(0).__getattr__("set_return").__call__(var2, var1.getlocal(0).__getattr__("curframe"));
      var1.setline(685);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_continue$39(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      var1.getlocal(0).__getattr__("set_continue").__call__(var2);
      var1.setline(690);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_jump$40(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyObject var3 = var1.getlocal(0).__getattr__("curindex")._add(Py.newInteger(1));
      PyObject var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(695);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, PyString.fromInterned("*** You can only jump within the bottom frame"));
         var1.setline(696);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         label29: {
            PyObject var4;
            try {
               var1.setline(698);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (var8.match(var1.getglobal("ValueError"))) {
                  var1.setline(700);
                  var4 = var1.getlocal(0).__getattr__("stdout");
                  Py.println(var4, PyString.fromInterned("*** The 'jump' command requires a line number."));
                  break label29;
               }

               throw var8;
            }

            try {
               var1.setline(705);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("curframe").__setattr__("f_lineno", var4);
               var4 = null;
               var1.setline(706);
               PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")).__getitem__(Py.newInteger(0)), var1.getlocal(1)});
               var1.getlocal(0).__getattr__("stack").__setitem__((PyObject)var1.getlocal(0).__getattr__("curindex"), var10);
               var4 = null;
               var1.setline(707);
               var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(0).__getattr__("curindex")));
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (!var9.match(var1.getglobal("ValueError"))) {
                  throw var9;
               }

               PyObject var5 = var9.value;
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(709);
               var5 = var1.getlocal(0).__getattr__("stdout");
               Py.printComma(var5, PyString.fromInterned("*** Jump failed:"));
               Py.println(var5, var1.getlocal(2));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject do_debug$41(PyFrame var1, ThreadState var2) {
      var1.setline(713);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.setline(714);
      PyObject var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(715);
      var3 = var1.getlocal(0).__getattr__("curframe_locals");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(716);
      var3 = var1.getglobal("Pdb").__call__(var2, var1.getlocal(0).__getattr__("completekey"), var1.getlocal(0).__getattr__("stdin"), var1.getlocal(0).__getattr__("stdout"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(717);
      var3 = PyString.fromInterned("(%s) ")._mod(var1.getlocal(0).__getattr__("prompt").__getattr__("strip").__call__(var2));
      var1.getlocal(4).__setattr__("prompt", var3);
      var3 = null;
      var1.setline(718);
      var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("ENTERING RECURSIVE DEBUGGER"));
      var1.setline(719);
      var1.getglobal("sys").__getattr__("call_tracing").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("run"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(720);
      var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("LEAVING RECURSIVE DEBUGGER"));
      var1.setline(721);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0).__getattr__("trace_dispatch"));
      var1.setline(722);
      var3 = var1.getlocal(4).__getattr__("lastcmd");
      var1.getlocal(0).__setattr__("lastcmd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_quit$42(PyFrame var1, ThreadState var2) {
      var1.setline(725);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_user_requested_quit", var3);
      var3 = null;
      var1.setline(726);
      var1.getlocal(0).__getattr__("set_quit").__call__(var2);
      var1.setline(727);
      var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject do_EOF$43(PyFrame var1, ThreadState var2) {
      var1.setline(733);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.printlnv(var3);
      var1.setline(734);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_user_requested_quit", var4);
      var3 = null;
      var1.setline(735);
      var1.getlocal(0).__getattr__("set_quit").__call__(var2);
      var1.setline(736);
      var4 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject do_args$44(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      PyObject var3 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_code");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(740);
      var3 = var1.getlocal(0).__getattr__("curframe_locals");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(741);
      var3 = var1.getlocal(2).__getattr__("co_argcount");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(742);
      if (var1.getlocal(2).__getattr__("co_flags")._and(Py.newInteger(4)).__nonzero__()) {
         var1.setline(742);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(743);
      if (var1.getlocal(2).__getattr__("co_flags")._and(Py.newInteger(8)).__nonzero__()) {
         var1.setline(743);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(744);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(4)).__iter__();

      while(true) {
         var1.setline(744);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(745);
         PyObject var5 = var1.getlocal(2).__getattr__("co_varnames").__getitem__(var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(746);
         var5 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var5, var1.getlocal(6));
         Py.printComma(var5, PyString.fromInterned("="));
         var1.setline(747);
         var5 = var1.getlocal(6);
         PyObject var10000 = var5._in(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(747);
            var5 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var5, var1.getlocal(3).__getitem__(var1.getlocal(6)));
         } else {
            var1.setline(748);
            var5 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var5, PyString.fromInterned("*** undefined ***"));
         }
      }
   }

   public PyObject do_retval$45(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      PyString var3 = PyString.fromInterned("__return__");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("curframe_locals"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(753);
         var4 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var4, var1.getlocal(0).__getattr__("curframe_locals").__getitem__(PyString.fromInterned("__return__")));
      } else {
         var1.setline(755);
         var4 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var4, PyString.fromInterned("*** Not yet returned!"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getval$46(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(760);
         PyObject var3 = var1.getglobal("eval").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals"), var1.getlocal(0).__getattr__("curframe_locals"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(763);
         PyObject var5 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(2, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(3, var7);
         var7 = null;
         var5 = null;
         var1.setline(764);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
            var1.setline(765);
            var5 = var1.getlocal(2);
            var1.setlocal(4, var5);
            var5 = null;
         } else {
            var1.setline(766);
            var5 = var1.getlocal(2).__getattr__("__name__");
            var1.setlocal(4, var5);
            var5 = null;
         }

         var1.setline(767);
         var5 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var5, PyString.fromInterned("***"));
         Py.printComma(var5, var1.getlocal(4)._add(PyString.fromInterned(":")));
         Py.println(var5, var1.getglobal("repr").__call__(var2, var1.getlocal(3)));
         var1.setline(768);
         throw Py.makeException();
      }
   }

   public PyObject do_p$47(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(772);
         PyObject var3 = var1.getlocal(0).__getattr__("stdout");
         Py.println(var3, var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("_getval").__call__(var2, var1.getlocal(1))));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(774);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_pp$48(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(778);
         var1.getglobal("pprint").__getattr__("pprint").__call__(var2, var1.getlocal(0).__getattr__("_getval").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("stdout"));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(780);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_list$49(PyFrame var1, ThreadState var2) {
      var1.setline(783);
      PyString var3 = PyString.fromInterned("list");
      var1.getlocal(0).__setattr__((String)"lastcmd", var3);
      var3 = null;
      var1.setline(784);
      PyObject var8 = var1.getglobal("None");
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(785);
      PyObject var10000;
      PyObject var4;
      PyObject var5;
      if (var1.getlocal(1).__nonzero__()) {
         try {
            var1.setline(787);
            var8 = var1.getglobal("eval").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)(new PyDictionary(Py.EmptyObjects)));
            var1.setlocal(3, var8);
            var3 = null;
            var1.setline(788);
            var8 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
            var10000 = var8._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(789);
               var8 = var1.getlocal(3);
               PyObject[] var9 = Py.unpackSequence(var8, 2);
               var5 = var9[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var9[1];
               var1.setlocal(2, var5);
               var5 = null;
               var3 = null;
               var1.setline(790);
               var8 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
               var1.setlocal(4, var8);
               var3 = null;
               var1.setline(791);
               var8 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var8);
               var3 = null;
               var1.setline(792);
               var8 = var1.getlocal(2);
               var10000 = var8._lt(var1.getlocal(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(794);
                  var8 = var1.getlocal(4)._add(var1.getlocal(2));
                  var1.setlocal(2, var8);
                  var3 = null;
               }
            } else {
               var1.setline(796);
               var8 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("int").__call__(var2, var1.getlocal(3))._sub(Py.newInteger(5)));
               var1.setlocal(4, var8);
               var3 = null;
            }
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(798);
            var4 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var4, PyString.fromInterned("*** Error in argument:"));
            Py.println(var4, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
            var1.setline(799);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(800);
         var8 = var1.getlocal(0).__getattr__("lineno");
         var10000 = var8._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(801);
            var8 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(0).__getattr__("curframe").__getattr__("f_lineno")._sub(Py.newInteger(5)));
            var1.setlocal(4, var8);
            var3 = null;
         } else {
            var1.setline(803);
            var8 = var1.getlocal(0).__getattr__("lineno")._add(Py.newInteger(1));
            var1.setlocal(4, var8);
            var3 = null;
         }
      }

      var1.setline(804);
      var8 = var1.getlocal(2);
      var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(805);
         var8 = var1.getlocal(4)._add(Py.newInteger(10));
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(806);
      var8 = var1.getlocal(0).__getattr__("curframe").__getattr__("f_code").__getattr__("co_filename");
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(807);
      var8 = var1.getlocal(0).__getattr__("get_file_breaks").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var8);
      var3 = null;

      try {
         var1.setline(809);
         var8 = var1.getglobal("range").__call__(var2, var1.getlocal(4), var1.getlocal(2)._add(Py.newInteger(1))).__iter__();

         while(true) {
            var1.setline(809);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(810);
            var5 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(5), var1.getlocal(7), var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals"));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(812);
            if (var1.getlocal(8).__not__().__nonzero__()) {
               var1.setline(813);
               var5 = var1.getlocal(0).__getattr__("stdout");
               Py.println(var5, PyString.fromInterned("[EOF]"));
               break;
            }

            var1.setline(816);
            var5 = var1.getglobal("repr").__call__(var2, var1.getlocal(7)).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(817);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
            var10000 = var5._lt(Py.newInteger(4));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(817);
               var5 = var1.getlocal(9)._add(PyString.fromInterned(" "));
               var1.setlocal(9, var5);
               var5 = null;
            }

            var1.setline(818);
            var5 = var1.getlocal(7);
            var10000 = var5._in(var1.getlocal(6));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(818);
               var5 = var1.getlocal(9)._add(PyString.fromInterned("B"));
               var1.setlocal(9, var5);
               var5 = null;
            } else {
               var1.setline(819);
               var5 = var1.getlocal(9)._add(PyString.fromInterned(" "));
               var1.setlocal(9, var5);
               var5 = null;
            }

            var1.setline(820);
            var5 = var1.getlocal(7);
            var10000 = var5._eq(var1.getlocal(0).__getattr__("curframe").__getattr__("f_lineno"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(821);
               var5 = var1.getlocal(9)._add(PyString.fromInterned("->"));
               var1.setlocal(9, var5);
               var5 = null;
            }

            var1.setline(822);
            var5 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var5, var1.getlocal(9)._add(PyString.fromInterned("\t"))._add(var1.getlocal(8)));
            var1.setline(823);
            var5 = var1.getlocal(7);
            var1.getlocal(0).__setattr__("lineno", var5);
            var5 = null;
         }
      } catch (Throwable var7) {
         PyException var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var10;
         }

         var1.setline(825);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_whatis$50(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(830);
         var3 = var1.getglobal("eval").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("curframe").__getattr__("f_globals"), var1.getlocal(0).__getattr__("curframe_locals"));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var9) {
         Py.setException(var9, var1);
         var1.setline(833);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(834);
         var4 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var4._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(835);
            var4 = var1.getlocal(3);
            var1.setlocal(5, var4);
            var4 = null;
         } else {
            var1.setline(836);
            var4 = var1.getlocal(3).__getattr__("__name__");
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(837);
         var4 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var4, PyString.fromInterned("***"));
         Py.printComma(var4, var1.getlocal(5)._add(PyString.fromInterned(":")));
         Py.println(var4, var1.getglobal("repr").__call__(var2, var1.getlocal(4)));
         var1.setline(838);
         var1.f_lasti = -1;
         return Py.None;
      }

      var1.setline(839);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;

      try {
         var1.setline(841);
         var3 = var1.getlocal(2).__getattr__("func_code");
         var1.setlocal(6, var3);
         var3 = null;
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(842);
      }

      var1.setline(843);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(844);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var3, PyString.fromInterned("Function"));
         Py.println(var3, var1.getlocal(6).__getattr__("co_name"));
         var1.setline(845);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         try {
            var1.setline(847);
            var3 = var1.getlocal(2).__getattr__("im_func").__getattr__("func_code");
            var1.setlocal(6, var3);
            var3 = null;
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(848);
         }

         var1.setline(849);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(850);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.printComma(var3, PyString.fromInterned("Method"));
            Py.println(var3, var1.getlocal(6).__getattr__("co_name"));
            var1.setline(851);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(853);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, var1.getglobal("type").__call__(var2, var1.getlocal(2)));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject do_alias$51(PyFrame var1, ThreadState var2) {
      var1.setline(856);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(857);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var1.setline(863);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var10000 = var3._in(var1.getlocal(0).__getattr__("aliases"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(864);
            var3 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var3, PyString.fromInterned("%s = %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("aliases").__getitem__(var1.getlocal(2).__getitem__(Py.newInteger(0)))})));
         } else {
            var1.setline(866);
            var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.getlocal(0).__getattr__("aliases").__setitem__(var1.getlocal(2).__getitem__(Py.newInteger(0)), var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(858);
         var3 = var1.getlocal(0).__getattr__("aliases").__getattr__("keys").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(859);
         var1.getlocal(3).__getattr__("sort").__call__(var2);
         var1.setline(860);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(860);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(862);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(861);
            PyObject var5 = var1.getlocal(0).__getattr__("stdout");
            Py.println(var5, PyString.fromInterned("%s = %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("aliases").__getitem__(var1.getlocal(4))})));
         }
      }
   }

   public PyObject do_unalias$52(PyFrame var1, ThreadState var2) {
      var1.setline(869);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(870);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(870);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(871);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var10000 = var3._in(var1.getlocal(0).__getattr__("aliases"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(872);
            var1.getlocal(0).__getattr__("aliases").__delitem__(var1.getlocal(2).__getitem__(Py.newInteger(0)));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject print_stack_trace$53(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(888);
         PyObject var6 = var1.getlocal(0).__getattr__("stack").__iter__();

         while(true) {
            var1.setline(888);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(889);
            var1.getlocal(0).__getattr__("print_stack_entry").__call__(var2, var1.getlocal(1));
         }
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var3;
         }

         var1.setline(891);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_stack_entry$54(PyFrame var1, ThreadState var2) {
      var1.setline(894);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(895);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("curframe"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(896);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var3, PyString.fromInterned(">"));
      } else {
         var1.setline(898);
         var3 = var1.getlocal(0).__getattr__("stdout");
         Py.printComma(var3, PyString.fromInterned(" "));
      }

      var1.setline(899);
      var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, var1.getlocal(0).__getattr__("format_stack_entry").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_help$55(PyFrame var1, ThreadState var2) {
      var1.setline(906);
      var1.getlocal(0).__getattr__("help_h").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_h$56(PyFrame var1, ThreadState var2) {
      var1.setline(909);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("h(elp)\nWithout argument, print the list of available commands.\nWith a command name as argument, print help about that command\n\"help pdb\" pipes the full documentation file to the $PAGER\n\"help exec\" gives help on the ! command"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_where$57(PyFrame var1, ThreadState var2) {
      var1.setline(916);
      var1.getlocal(0).__getattr__("help_w").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_w$58(PyFrame var1, ThreadState var2) {
      var1.setline(919);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("w(here)\nPrint a stack trace, with the most recent frame at the bottom.\nAn arrow indicates the \"current frame\", which determines the\ncontext of most commands.  'bt' is an alias for this command."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_down$59(PyFrame var1, ThreadState var2) {
      var1.setline(927);
      var1.getlocal(0).__getattr__("help_d").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_d$60(PyFrame var1, ThreadState var2) {
      var1.setline(930);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("d(own)\nMove the current frame one level down in the stack trace\n(to a newer frame)."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_up$61(PyFrame var1, ThreadState var2) {
      var1.setline(935);
      var1.getlocal(0).__getattr__("help_u").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_u$62(PyFrame var1, ThreadState var2) {
      var1.setline(938);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("u(p)\nMove the current frame one level up in the stack trace\n(to an older frame)."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_break$63(PyFrame var1, ThreadState var2) {
      var1.setline(943);
      var1.getlocal(0).__getattr__("help_b").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_b$64(PyFrame var1, ThreadState var2) {
      var1.setline(946);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("b(reak) ([file:]lineno | function) [, condition]\nWith a line number argument, set a break there in the current\nfile.  With a function name, set a break at first executable line\nof that function.  Without argument, list all breaks.  If a second\nargument is present, it is a string specifying an expression\nwhich must evaluate to true before the breakpoint is honored.\n\nThe line number may be prefixed with a filename and a colon,\nto specify a breakpoint in another file (probably one that\nhasn't been loaded yet).  The file is searched for on sys.path;\nthe .py suffix may be omitted."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_clear$65(PyFrame var1, ThreadState var2) {
      var1.setline(959);
      var1.getlocal(0).__getattr__("help_cl").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_cl$66(PyFrame var1, ThreadState var2) {
      var1.setline(962);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("cl(ear) filename:lineno"));
      var1.setline(963);
      var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("cl(ear) [bpnumber [bpnumber...]]\nWith a space separated list of breakpoint numbers, clear\nthose breakpoints.  Without argument, clear all breaks (but\nfirst ask confirmation).  With a filename:lineno argument,\nclear all breaks at that line in that file.\n\nNote that the argument is different from previous versions of\nthe debugger (in python distributions 1.5.1 and before) where\na linenumber was used instead of either filename:lineno or\nbreakpoint numbers."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_tbreak$67(PyFrame var1, ThreadState var2) {
      var1.setline(975);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("tbreak  same arguments as break, but breakpoint\nis removed when first hit."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_enable$68(PyFrame var1, ThreadState var2) {
      var1.setline(979);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("enable bpnumber [bpnumber ...]\nEnables the breakpoints given as a space separated list of\nbp numbers."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_disable$69(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("disable bpnumber [bpnumber ...]\nDisables the breakpoints given as a space separated list of\nbp numbers."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_ignore$70(PyFrame var1, ThreadState var2) {
      var1.setline(989);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("ignore bpnumber count\nSets the ignore count for the given breakpoint number.  A breakpoint\nbecomes active when the ignore count is zero.  When non-zero, the\ncount is decremented each time the breakpoint is reached and the\nbreakpoint is not disabled and any associated condition evaluates\nto true."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_condition$71(PyFrame var1, ThreadState var2) {
      var1.setline(997);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("condition bpnumber str_condition\nstr_condition is a string specifying an expression which\nmust evaluate to true before the breakpoint is honored.\nIf str_condition is absent, any existing condition is removed;\ni.e., the breakpoint is made unconditional."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_step$72(PyFrame var1, ThreadState var2) {
      var1.setline(1004);
      var1.getlocal(0).__getattr__("help_s").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_s$73(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("s(tep)\nExecute the current line, stop at the first possible occasion\n(either in a function that is called or in the current function)."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_until$74(PyFrame var1, ThreadState var2) {
      var1.setline(1012);
      var1.getlocal(0).__getattr__("help_unt").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_unt$75(PyFrame var1, ThreadState var2) {
      var1.setline(1015);
      Py.println(PyString.fromInterned("unt(il)\nContinue execution until the line with a number greater than the current\none is reached or until the current frame returns"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_next$76(PyFrame var1, ThreadState var2) {
      var1.setline(1020);
      var1.getlocal(0).__getattr__("help_n").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_n$77(PyFrame var1, ThreadState var2) {
      var1.setline(1023);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("n(ext)\nContinue execution until the next line in the current function\nis reached or it returns."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_return$78(PyFrame var1, ThreadState var2) {
      var1.setline(1028);
      var1.getlocal(0).__getattr__("help_r").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_r$79(PyFrame var1, ThreadState var2) {
      var1.setline(1031);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("r(eturn)\nContinue execution until the current function returns."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_continue$80(PyFrame var1, ThreadState var2) {
      var1.setline(1035);
      var1.getlocal(0).__getattr__("help_c").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_cont$81(PyFrame var1, ThreadState var2) {
      var1.setline(1038);
      var1.getlocal(0).__getattr__("help_c").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_c$82(PyFrame var1, ThreadState var2) {
      var1.setline(1041);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("c(ont(inue))\nContinue execution, only stop when a breakpoint is encountered."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_jump$83(PyFrame var1, ThreadState var2) {
      var1.setline(1045);
      var1.getlocal(0).__getattr__("help_j").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_j$84(PyFrame var1, ThreadState var2) {
      var1.setline(1048);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("j(ump) lineno\nSet the next line that will be executed."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_debug$85(PyFrame var1, ThreadState var2) {
      var1.setline(1052);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("debug code\nEnter a recursive debugger that steps through the code argument\n(which is an arbitrary expression or statement to be executed\nin the current environment)."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_list$86(PyFrame var1, ThreadState var2) {
      var1.setline(1058);
      var1.getlocal(0).__getattr__("help_l").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_l$87(PyFrame var1, ThreadState var2) {
      var1.setline(1061);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("l(ist) [first [,last]]\nList source code for the current file.\nWithout arguments, list 11 lines around the current line\nor continue the previous listing.\nWith one argument, list 11 lines starting at that line.\nWith two arguments, list the given range;\nif the second argument is less than the first, it is a count."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_args$88(PyFrame var1, ThreadState var2) {
      var1.setline(1070);
      var1.getlocal(0).__getattr__("help_a").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_a$89(PyFrame var1, ThreadState var2) {
      var1.setline(1073);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("a(rgs)\nPrint the arguments of the current function."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_p$90(PyFrame var1, ThreadState var2) {
      var1.setline(1077);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("p expression\nPrint the value of the expression."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_pp$91(PyFrame var1, ThreadState var2) {
      var1.setline(1081);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("pp expression\nPretty-print the value of the expression."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_exec$92(PyFrame var1, ThreadState var2) {
      var1.setline(1085);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("(!) statement\nExecute the (one-line) statement in the context of\nthe current stack frame.\nThe exclamation point can be omitted unless the first word\nof the statement resembles a debugger command.\nTo assign to a global variable you must always prefix the\ncommand with a 'global' command, e.g.:\n(Pdb) global list_options; list_options = ['-l']\n(Pdb)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_run$93(PyFrame var1, ThreadState var2) {
      var1.setline(1096);
      Py.println(PyString.fromInterned("run [args...]\nRestart the debugged python program. If a string is supplied, it is\nsplitted with \"shlex\" and the result is used as the new sys.argv.\nHistory, breakpoints, actions and debugger options are preserved.\n\"restart\" is an alias for \"run\"."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_quit$94(PyFrame var1, ThreadState var2) {
      var1.setline(1105);
      var1.getlocal(0).__getattr__("help_q").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_q$95(PyFrame var1, ThreadState var2) {
      var1.setline(1108);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("q(uit) or exit - Quit from the debugger.\nThe program being executed is aborted."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_whatis$96(PyFrame var1, ThreadState var2) {
      var1.setline(1114);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("whatis arg\nPrints the type of the argument."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_EOF$97(PyFrame var1, ThreadState var2) {
      var1.setline(1118);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("EOF\nHandles the receipt of EOF as a command."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_alias$98(PyFrame var1, ThreadState var2) {
      var1.setline(1122);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("alias [name [command [parameter parameter ...]]]\nCreates an alias called 'name' the executes 'command'.  The command\nmust *not* be enclosed in quotes.  Replaceable parameters are\nindicated by %1, %2, and so on, while %* is replaced by all the\nparameters.  If no command is given, the current alias for name\nis shown. If no name is given, all aliases are listed.\n\nAliases may be nested and can contain anything that can be\nlegally typed at the pdb prompt.  Note!  You *can* override\ninternal pdb commands with aliases!  Those internal commands\nare then hidden until the alias is removed.  Aliasing is recursively\napplied to the first word of the command line; all other words\nin the line are left alone.\n\nSome useful aliases (especially when placed in the .pdbrc file) are:\n\n#Print instance variables (usage \"pi classInst\")\nalias pi for k in %1.__dict__.keys(): print \"%1.\",k,\"=\",%1.__dict__[k]\n\n#Print instance variables in self\nalias ps pi self\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_unalias$99(PyFrame var1, ThreadState var2) {
      var1.setline(1146);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("unalias name\nDeletes the specified alias."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_commands$100(PyFrame var1, ThreadState var2) {
      var1.setline(1150);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout");
      Py.println(var3, PyString.fromInterned("commands [bpnumber]\n(com) ...\n(com) end\n(Pdb)\n\nSpecify a list of commands for breakpoint number bpnumber.  The\ncommands themselves appear on the following lines.  Type a line\ncontaining just 'end' to terminate the commands.\n\nTo remove all commands from a breakpoint, type commands and\nfollow it immediately with  end; that is, give no commands.\n\nWith no bpnumber argument, commands refers to the last\nbreakpoint set.\n\nYou can use breakpoint commands to start your program up again.\nSimply use the continue command, or step, or any other\ncommand that resumes execution.\n\nSpecifying any command resuming execution (currently continue,\nstep, next, return, jump, quit and their abbreviations) terminates\nthe command list (as if that command was immediately followed by end).\nThis is because any time you resume execution\n(even with a simple next or step), you may encounter\nanother breakpoint--which could have its own command list, leading to\nambiguities about which list to execute.\n\n   If you use the 'silent' command in the command list, the\nusual message about stopping at a breakpoint is not printed.  This may\nbe desirable for breakpoints that are to print a specific message and\nthen continue.  If none of the other commands print anything, you\nsee no sign that the breakpoint was reached.\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_pdb$101(PyFrame var1, ThreadState var2) {
      var1.setline(1185);
      var1.getglobal("help").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject lookupmodule$102(PyFrame var1, ThreadState var2) {
      var1.setline(1192);
      PyString.fromInterned("Helper function for break/clear parsing -- may be overridden.\n\n        lookupmodule() translates (possibly incomplete) file or module name\n        into an absolute file name.\n        ");
      var1.setline(1193);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(1194);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1195);
         PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("sys").__getattr__("path").__getitem__(Py.newInteger(0)), var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1196);
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2));
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(2));
            var10000 = var4._eq(var1.getlocal(0).__getattr__("mainpyfile"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1197);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1198);
            var4 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(1));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(1199);
            var4 = var1.getlocal(4);
            var10000 = var4._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1200);
               var4 = var1.getlocal(1)._add(PyString.fromInterned(".py"));
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(1201);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(1202);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1203);
               var4 = var1.getglobal("sys").__getattr__("path").__iter__();

               do {
                  var1.setline(1203);
                  PyObject var7 = var4.__iternext__();
                  if (var7 == null) {
                     var1.setline(1209);
                     var3 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(5, var7);

                  while(true) {
                     var1.setline(1204);
                     if (!var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(5)).__nonzero__()) {
                        var1.setline(1206);
                        var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(1));
                        var1.setlocal(6, var6);
                        var6 = null;
                        var1.setline(1207);
                        break;
                     }

                     var1.setline(1205);
                     var6 = var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(5));
                     var1.setlocal(5, var6);
                     var6 = null;
                  }
               } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)).__nonzero__());

               var1.setline(1208);
               var3 = var1.getlocal(6);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _runscript$103(PyFrame var1, ThreadState var2) {
      var1.setline(1217);
      PyObject var3 = imp.importOne("__main__", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1218);
      var1.getlocal(2).__getattr__("__dict__").__getattr__("clear").__call__(var2);
      var1.setline(1219);
      var1.getlocal(2).__getattr__("__dict__").__getattr__("update").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("__name__"), PyString.fromInterned("__main__"), PyString.fromInterned("__file__"), var1.getlocal(1), PyString.fromInterned("__builtins__"), var1.getglobal("__builtins__")})));
      var1.setline(1229);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_wait_for_mainpyfile", var4);
      var3 = null;
      var1.setline(1230);
      var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("mainpyfile", var3);
      var3 = null;
      var1.setline(1231);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_user_requested_quit", var4);
      var3 = null;
      var1.setline(1232);
      var3 = PyString.fromInterned("execfile(%r)")._mod(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1233);
      var1.getlocal(0).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$104(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      var1.getglobal("Pdb").__call__(var2).__getattr__("run").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runeval$105(PyFrame var1, ThreadState var2) {
      var1.setline(1241);
      PyObject var3 = var1.getglobal("Pdb").__call__(var2).__getattr__("runeval").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runctx$106(PyFrame var1, ThreadState var2) {
      var1.setline(1245);
      var1.getglobal("run").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runcall$107(PyFrame var1, ThreadState var2) {
      var1.setline(1248);
      PyObject var10000 = var1.getglobal("Pdb").__call__(var2).__getattr__("runcall");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject set_trace$108(PyFrame var1, ThreadState var2) {
      var1.setline(1251);
      var1.getglobal("Pdb").__call__(var2).__getattr__("set_trace").__call__(var2, var1.getglobal("sys").__getattr__("_getframe").__call__(var2).__getattr__("f_back"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject post_mortem$109(PyFrame var1, ThreadState var2) {
      var1.setline(1257);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1260);
         var3 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(1261);
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1262);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A valid traceback must be passed if no exception is being handled")));
         }
      }

      var1.setline(1265);
      var3 = var1.getglobal("Pdb").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1266);
      var1.getlocal(1).__getattr__("reset").__call__(var2);
      var1.setline(1267);
      var1.getlocal(1).__getattr__("interaction").__call__(var2, var1.getglobal("None"), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pm$110(PyFrame var1, ThreadState var2) {
      var1.setline(1270);
      var1.getglobal("post_mortem").__call__(var2, var1.getglobal("sys").__getattr__("last_traceback"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$111(PyFrame var1, ThreadState var2) {
      var1.setline(1278);
      var1.getglobal("run").__call__(var2, var1.getglobal("TESTCMD"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help$112(PyFrame var1, ThreadState var2) {
      var1.setline(1282);
      PyObject var3 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(1282);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1289);
            Py.printComma(PyString.fromInterned("Sorry, can't find the help file \"pdb.doc\""));
            var1.setline(1290);
            Py.println(PyString.fromInterned("along the Python search path"));
            break;
         }

         var1.setlocal(0, var4);
         var1.setline(1283);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("pdb.doc"));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(1284);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1285);
            var5 = var1.getglobal("os").__getattr__("system").__call__(var2, PyString.fromInterned("${PAGER-more} ")._add(var1.getlocal(1)));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(1286);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1286);
               Py.printComma(PyString.fromInterned("*** Pager exit status:"));
               Py.println(var1.getlocal(2));
            }
            break;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$113(PyFrame var1, ThreadState var2) {
      var1.setline(1293);
      PyObject var10000 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("--help"), PyString.fromInterned("-h")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1294);
         Py.println(PyString.fromInterned("usage: pdb.py scriptfile [arg] ..."));
         var1.setline(1295);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      }

      var1.setline(1297);
      var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1298);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(1299);
         Py.printComma(PyString.fromInterned("Error:"));
         Py.printComma(var1.getlocal(0));
         Py.println(PyString.fromInterned("does not exist"));
         var1.setline(1300);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(1302);
      var1.getglobal("sys").__getattr__("argv").__delitem__((PyObject)Py.newInteger(0));
      var1.setline(1305);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(0));
      var1.getglobal("sys").__getattr__("path").__setitem__((PyObject)Py.newInteger(0), var3);
      var3 = null;
      var1.setline(1311);
      var3 = var1.getglobal("Pdb").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(1312);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         try {
            var1.setline(1314);
            var1.getlocal(1).__getattr__("_runscript").__call__(var2, var1.getlocal(0));
            var1.setline(1315);
            if (var1.getlocal(1).__getattr__("_user_requested_quit").__nonzero__()) {
               break;
            }

            var1.setline(1317);
            Py.println(PyString.fromInterned("The program finished and will be restarted"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("Restart"))) {
               var1.setline(1319);
               Py.printComma(PyString.fromInterned("Restarting"));
               Py.printComma(var1.getlocal(0));
               Py.println(PyString.fromInterned("with arguments:"));
               var1.setline(1320);
               Py.println(PyString.fromInterned("\t")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null))));
            } else if (var6.match(var1.getglobal("SystemExit"))) {
               var1.setline(1323);
               Py.printComma(PyString.fromInterned("The program exited via sys.exit(). Exit status: "));
               var1.setline(1324);
               Py.println(var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1)));
            } else {
               var1.setline(1326);
               var1.getglobal("traceback").__getattr__("print_exc").__call__(var2);
               var1.setline(1327);
               Py.println(PyString.fromInterned("Uncaught exception. Entering post mortem debugging"));
               var1.setline(1328);
               Py.println(PyString.fromInterned("Running 'cont' or 'step' will restart the program"));
               var1.setline(1329);
               PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1330);
               var1.getlocal(1).__getattr__("interaction").__call__(var2, var1.getglobal("None"), var1.getlocal(2));
               var1.setline(1331);
               Py.println(PyString.fromInterned("Post mortem debugger finished. The ")._add(var1.getlocal(0))._add(PyString.fromInterned(" will be restarted")));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public pdb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Restart$1 = Py.newCode(0, var2, var1, "Restart", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"funcname", "filename", "cre", "fp", "lineno", "answer", "line"};
      find_function$2 = Py.newCode(2, var2, var1, "find_function", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Pdb$3 = Py.newCode(0, var2, var1, "Pdb", 59, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "completekey", "stdin", "stdout", "skip", "readline", "envHome", "rcFile", "line"};
      __init__$4 = Py.newCode(5, var2, var1, "__init__", 61, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$5 = Py.newCode(1, var2, var1, "reset", 107, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      forget$6 = Py.newCode(1, var2, var1, "forget", 111, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "t"};
      setup$7 = Py.newCode(3, var2, var1, "setup", 117, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rcLines", "line"};
      execRcLines$8 = Py.newCode(1, var2, var1, "execRcLines", 128, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "argument_list"};
      user_call$9 = Py.newCode(3, var2, var1, "user_call", 141, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      user_line$10 = Py.newCode(2, var2, var1, "user_line", 150, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "currentbp", "lastcmd_back", "line"};
      bp_commands$11 = Py.newCode(2, var2, var1, "bp_commands", 160, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "return_value"};
      user_return$12 = Py.newCode(3, var2, var1, "user_return", 184, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "exc_info", "exc_type", "exc_value", "exc_traceback", "exc_type_name"};
      user_exception$13 = Py.newCode(3, var2, var1, "user_exception", 192, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "traceback"};
      interaction$14 = Py.newCode(3, var2, var1, "interaction", 207, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      displayhook$15 = Py.newCode(2, var2, var1, "displayhook", 213, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "locals", "globals", "code", "save_stdout", "save_stdin", "save_displayhook", "t", "v", "exc_type_name"};
      default$16 = Py.newCode(2, var2, var1, "default", 221, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line", "args", "ii", "tmpArg", "marker", "next"};
      precmd$17 = Py.newCode(2, var2, var1, "precmd", 246, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      onecmd$18 = Py.newCode(2, var2, var1, "onecmd", 271, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "cmd", "arg", "cmdlist", "func"};
      handle_command_def$19 = Py.newCode(2, var2, var1, "handle_command_def", 283, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "bnum", "prompt_back"};
      do_commands$20 = Py.newCode(2, var2, var1, "do_commands", 317, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "temporary", "bp", "filename", "lineno", "cond", "comma", "colon", "funcname", "f", "msg", "func", "code", "ok", "ln", "line", "err"};
      do_break$21 = Py.newCode(3, var2, var1, "do_break", 344, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      defaultFile$22 = Py.newCode(1, var2, var1, "defaultFile", 427, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_tbreak$23 = Py.newCode(2, var2, var1, "do_tbreak", 436, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "identifier", "failed", "idstring", "id", "parts", "fname", "item", "f", "answer"};
      lineinfo$24 = Py.newCode(2, var2, var1, "lineinfo", 439, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno", "globs", "line"};
      checkline$25 = Py.newCode(3, var2, var1, "checkline", 472, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args", "i", "bp"};
      do_enable$26 = Py.newCode(2, var2, var1, "do_enable", 493, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args", "i", "bp"};
      do_disable$27 = Py.newCode(2, var2, var1, "do_disable", 510, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args", "bpnum", "cond", "bp"};
      do_condition$28 = Py.newCode(2, var2, var1, "do_condition", 527, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args", "bpnum", "count", "bp", "reply"};
      do_ignore$29 = Py.newCode(2, var2, var1, "do_ignore", 552, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "reply", "i", "filename", "lineno", "err", "numberlist"};
      do_clear$30 = Py.newCode(2, var2, var1, "do_clear", 584, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_where$31 = Py.newCode(2, var2, var1, "do_where", 629, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_up$32 = Py.newCode(2, var2, var1, "do_up", 634, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_down$33 = Py.newCode(2, var2, var1, "do_down", 645, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_until$34 = Py.newCode(2, var2, var1, "do_until", 656, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_step$35 = Py.newCode(2, var2, var1, "do_step", 661, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_next$36 = Py.newCode(2, var2, var1, "do_next", 666, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "shlex", "argv0"};
      do_run$37 = Py.newCode(2, var2, var1, "do_run", 671, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_return$38 = Py.newCode(2, var2, var1, "do_return", 683, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_continue$39 = Py.newCode(2, var2, var1, "do_continue", 688, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "e"};
      do_jump$40 = Py.newCode(2, var2, var1, "do_jump", 693, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "globals", "locals", "p"};
      do_debug$41 = Py.newCode(2, var2, var1, "do_debug", 712, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_quit$42 = Py.newCode(2, var2, var1, "do_quit", 724, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_EOF$43 = Py.newCode(2, var2, var1, "do_EOF", 732, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "co", "dict", "n", "i", "name"};
      do_args$44 = Py.newCode(2, var2, var1, "do_args", 738, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_retval$45 = Py.newCode(2, var2, var1, "do_retval", 751, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "t", "v", "exc_type_name"};
      _getval$46 = Py.newCode(2, var2, var1, "_getval", 758, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_p$47 = Py.newCode(2, var2, var1, "do_p", 770, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_pp$48 = Py.newCode(2, var2, var1, "do_pp", 776, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "last", "x", "first", "filename", "breaklist", "lineno", "line", "s"};
      do_list$49 = Py.newCode(2, var2, var1, "do_list", 782, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "value", "t", "v", "exc_type_name", "code"};
      do_whatis$50 = Py.newCode(2, var2, var1, "do_whatis", 828, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args", "keys", "alias"};
      do_alias$51 = Py.newCode(2, var2, var1, "do_alias", 855, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "args"};
      do_unalias$52 = Py.newCode(2, var2, var1, "do_unalias", 868, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame_lineno"};
      print_stack_trace$53 = Py.newCode(1, var2, var1, "print_stack_trace", 886, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame_lineno", "prompt_prefix", "frame", "lineno"};
      print_stack_entry$54 = Py.newCode(3, var2, var1, "print_stack_entry", 893, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_help$55 = Py.newCode(1, var2, var1, "help_help", 905, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_h$56 = Py.newCode(1, var2, var1, "help_h", 908, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_where$57 = Py.newCode(1, var2, var1, "help_where", 915, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_w$58 = Py.newCode(1, var2, var1, "help_w", 918, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_down$59 = Py.newCode(1, var2, var1, "help_down", 926, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_d$60 = Py.newCode(1, var2, var1, "help_d", 929, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_up$61 = Py.newCode(1, var2, var1, "help_up", 934, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_u$62 = Py.newCode(1, var2, var1, "help_u", 937, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_break$63 = Py.newCode(1, var2, var1, "help_break", 942, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_b$64 = Py.newCode(1, var2, var1, "help_b", 945, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_clear$65 = Py.newCode(1, var2, var1, "help_clear", 958, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_cl$66 = Py.newCode(1, var2, var1, "help_cl", 961, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_tbreak$67 = Py.newCode(1, var2, var1, "help_tbreak", 974, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_enable$68 = Py.newCode(1, var2, var1, "help_enable", 978, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_disable$69 = Py.newCode(1, var2, var1, "help_disable", 983, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_ignore$70 = Py.newCode(1, var2, var1, "help_ignore", 988, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_condition$71 = Py.newCode(1, var2, var1, "help_condition", 996, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_step$72 = Py.newCode(1, var2, var1, "help_step", 1003, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_s$73 = Py.newCode(1, var2, var1, "help_s", 1006, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_until$74 = Py.newCode(1, var2, var1, "help_until", 1011, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_unt$75 = Py.newCode(1, var2, var1, "help_unt", 1014, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_next$76 = Py.newCode(1, var2, var1, "help_next", 1019, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_n$77 = Py.newCode(1, var2, var1, "help_n", 1022, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_return$78 = Py.newCode(1, var2, var1, "help_return", 1027, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_r$79 = Py.newCode(1, var2, var1, "help_r", 1030, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_continue$80 = Py.newCode(1, var2, var1, "help_continue", 1034, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_cont$81 = Py.newCode(1, var2, var1, "help_cont", 1037, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_c$82 = Py.newCode(1, var2, var1, "help_c", 1040, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_jump$83 = Py.newCode(1, var2, var1, "help_jump", 1044, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_j$84 = Py.newCode(1, var2, var1, "help_j", 1047, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_debug$85 = Py.newCode(1, var2, var1, "help_debug", 1051, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_list$86 = Py.newCode(1, var2, var1, "help_list", 1057, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_l$87 = Py.newCode(1, var2, var1, "help_l", 1060, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_args$88 = Py.newCode(1, var2, var1, "help_args", 1069, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_a$89 = Py.newCode(1, var2, var1, "help_a", 1072, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_p$90 = Py.newCode(1, var2, var1, "help_p", 1076, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_pp$91 = Py.newCode(1, var2, var1, "help_pp", 1080, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_exec$92 = Py.newCode(1, var2, var1, "help_exec", 1084, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_run$93 = Py.newCode(1, var2, var1, "help_run", 1095, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_quit$94 = Py.newCode(1, var2, var1, "help_quit", 1104, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_q$95 = Py.newCode(1, var2, var1, "help_q", 1107, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_whatis$96 = Py.newCode(1, var2, var1, "help_whatis", 1113, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_EOF$97 = Py.newCode(1, var2, var1, "help_EOF", 1117, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_alias$98 = Py.newCode(1, var2, var1, "help_alias", 1121, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_unalias$99 = Py.newCode(1, var2, var1, "help_unalias", 1145, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_commands$100 = Py.newCode(1, var2, var1, "help_commands", 1149, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_pdb$101 = Py.newCode(1, var2, var1, "help_pdb", 1184, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f", "root", "ext", "dirname", "fullname"};
      lookupmodule$102 = Py.newCode(2, var2, var1, "lookupmodule", 1187, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "__main__", "statement"};
      _runscript$103 = Py.newCode(2, var2, var1, "_runscript", 1211, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"statement", "globals", "locals"};
      run$104 = Py.newCode(3, var2, var1, "run", 1237, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"expression", "globals", "locals"};
      runeval$105 = Py.newCode(3, var2, var1, "runeval", 1240, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"statement", "globals", "locals"};
      runctx$106 = Py.newCode(3, var2, var1, "runctx", 1243, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwds"};
      runcall$107 = Py.newCode(2, var2, var1, "runcall", 1247, true, true, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      set_trace$108 = Py.newCode(0, var2, var1, "set_trace", 1250, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t", "p"};
      post_mortem$109 = Py.newCode(1, var2, var1, "post_mortem", 1255, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      pm$110 = Py.newCode(0, var2, var1, "pm", 1269, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test$111 = Py.newCode(0, var2, var1, "test", 1277, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dirname", "fullname", "sts"};
      help$112 = Py.newCode(0, var2, var1, "help", 1281, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mainpyfile", "pdb", "t"};
      main$113 = Py.newCode(0, var2, var1, "main", 1292, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pdb$py("pdb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pdb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Restart$1(var2, var3);
         case 2:
            return this.find_function$2(var2, var3);
         case 3:
            return this.Pdb$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.reset$5(var2, var3);
         case 6:
            return this.forget$6(var2, var3);
         case 7:
            return this.setup$7(var2, var3);
         case 8:
            return this.execRcLines$8(var2, var3);
         case 9:
            return this.user_call$9(var2, var3);
         case 10:
            return this.user_line$10(var2, var3);
         case 11:
            return this.bp_commands$11(var2, var3);
         case 12:
            return this.user_return$12(var2, var3);
         case 13:
            return this.user_exception$13(var2, var3);
         case 14:
            return this.interaction$14(var2, var3);
         case 15:
            return this.displayhook$15(var2, var3);
         case 16:
            return this.default$16(var2, var3);
         case 17:
            return this.precmd$17(var2, var3);
         case 18:
            return this.onecmd$18(var2, var3);
         case 19:
            return this.handle_command_def$19(var2, var3);
         case 20:
            return this.do_commands$20(var2, var3);
         case 21:
            return this.do_break$21(var2, var3);
         case 22:
            return this.defaultFile$22(var2, var3);
         case 23:
            return this.do_tbreak$23(var2, var3);
         case 24:
            return this.lineinfo$24(var2, var3);
         case 25:
            return this.checkline$25(var2, var3);
         case 26:
            return this.do_enable$26(var2, var3);
         case 27:
            return this.do_disable$27(var2, var3);
         case 28:
            return this.do_condition$28(var2, var3);
         case 29:
            return this.do_ignore$29(var2, var3);
         case 30:
            return this.do_clear$30(var2, var3);
         case 31:
            return this.do_where$31(var2, var3);
         case 32:
            return this.do_up$32(var2, var3);
         case 33:
            return this.do_down$33(var2, var3);
         case 34:
            return this.do_until$34(var2, var3);
         case 35:
            return this.do_step$35(var2, var3);
         case 36:
            return this.do_next$36(var2, var3);
         case 37:
            return this.do_run$37(var2, var3);
         case 38:
            return this.do_return$38(var2, var3);
         case 39:
            return this.do_continue$39(var2, var3);
         case 40:
            return this.do_jump$40(var2, var3);
         case 41:
            return this.do_debug$41(var2, var3);
         case 42:
            return this.do_quit$42(var2, var3);
         case 43:
            return this.do_EOF$43(var2, var3);
         case 44:
            return this.do_args$44(var2, var3);
         case 45:
            return this.do_retval$45(var2, var3);
         case 46:
            return this._getval$46(var2, var3);
         case 47:
            return this.do_p$47(var2, var3);
         case 48:
            return this.do_pp$48(var2, var3);
         case 49:
            return this.do_list$49(var2, var3);
         case 50:
            return this.do_whatis$50(var2, var3);
         case 51:
            return this.do_alias$51(var2, var3);
         case 52:
            return this.do_unalias$52(var2, var3);
         case 53:
            return this.print_stack_trace$53(var2, var3);
         case 54:
            return this.print_stack_entry$54(var2, var3);
         case 55:
            return this.help_help$55(var2, var3);
         case 56:
            return this.help_h$56(var2, var3);
         case 57:
            return this.help_where$57(var2, var3);
         case 58:
            return this.help_w$58(var2, var3);
         case 59:
            return this.help_down$59(var2, var3);
         case 60:
            return this.help_d$60(var2, var3);
         case 61:
            return this.help_up$61(var2, var3);
         case 62:
            return this.help_u$62(var2, var3);
         case 63:
            return this.help_break$63(var2, var3);
         case 64:
            return this.help_b$64(var2, var3);
         case 65:
            return this.help_clear$65(var2, var3);
         case 66:
            return this.help_cl$66(var2, var3);
         case 67:
            return this.help_tbreak$67(var2, var3);
         case 68:
            return this.help_enable$68(var2, var3);
         case 69:
            return this.help_disable$69(var2, var3);
         case 70:
            return this.help_ignore$70(var2, var3);
         case 71:
            return this.help_condition$71(var2, var3);
         case 72:
            return this.help_step$72(var2, var3);
         case 73:
            return this.help_s$73(var2, var3);
         case 74:
            return this.help_until$74(var2, var3);
         case 75:
            return this.help_unt$75(var2, var3);
         case 76:
            return this.help_next$76(var2, var3);
         case 77:
            return this.help_n$77(var2, var3);
         case 78:
            return this.help_return$78(var2, var3);
         case 79:
            return this.help_r$79(var2, var3);
         case 80:
            return this.help_continue$80(var2, var3);
         case 81:
            return this.help_cont$81(var2, var3);
         case 82:
            return this.help_c$82(var2, var3);
         case 83:
            return this.help_jump$83(var2, var3);
         case 84:
            return this.help_j$84(var2, var3);
         case 85:
            return this.help_debug$85(var2, var3);
         case 86:
            return this.help_list$86(var2, var3);
         case 87:
            return this.help_l$87(var2, var3);
         case 88:
            return this.help_args$88(var2, var3);
         case 89:
            return this.help_a$89(var2, var3);
         case 90:
            return this.help_p$90(var2, var3);
         case 91:
            return this.help_pp$91(var2, var3);
         case 92:
            return this.help_exec$92(var2, var3);
         case 93:
            return this.help_run$93(var2, var3);
         case 94:
            return this.help_quit$94(var2, var3);
         case 95:
            return this.help_q$95(var2, var3);
         case 96:
            return this.help_whatis$96(var2, var3);
         case 97:
            return this.help_EOF$97(var2, var3);
         case 98:
            return this.help_alias$98(var2, var3);
         case 99:
            return this.help_unalias$99(var2, var3);
         case 100:
            return this.help_commands$100(var2, var3);
         case 101:
            return this.help_pdb$101(var2, var3);
         case 102:
            return this.lookupmodule$102(var2, var3);
         case 103:
            return this._runscript$103(var2, var3);
         case 104:
            return this.run$104(var2, var3);
         case 105:
            return this.runeval$105(var2, var3);
         case 106:
            return this.runctx$106(var2, var3);
         case 107:
            return this.runcall$107(var2, var3);
         case 108:
            return this.set_trace$108(var2, var3);
         case 109:
            return this.post_mortem$109(var2, var3);
         case 110:
            return this.pm$110(var2, var3);
         case 111:
            return this.test$111(var2, var3);
         case 112:
            return this.help$112(var2, var3);
         case 113:
            return this.main$113(var2, var3);
         default:
            return null;
      }
   }
}
