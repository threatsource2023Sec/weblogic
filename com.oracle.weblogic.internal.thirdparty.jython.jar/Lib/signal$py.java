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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("signal.py")
public class signal$py extends PyFunctionTable implements PyRunnable {
   static signal$py self;
   static final PyCode f$0;
   static final PyCode _init_signals$1;
   static final PyCode JythonSignalHandler$2;
   static final PyCode __init__$3;
   static final PyCode handle$4;
   static final PyCode signal$5;
   static final PyCode _register_signal$6;
   static final PyCode getsignal$7;
   static final PyCode default_int_handler$8;
   static final PyCode pause$9;
   static final PyCode _alarm_handler$10;
   static final PyCode _Alarm$11;
   static final PyCode __init__$12;
   static final PyCode start$13;
   static final PyCode cancel$14;
   static final PyCode alarm$15;
   static final PyCode raise_alarm$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\n    This module provides mechanisms to use signal handlers in Python.\n\n    Functions:\n\n    signal(sig,action) -- set the action for a given signal (done)\n    pause(sig) -- wait until a signal arrives [Unix only]\n    alarm(seconds) -- cause SIGALRM after a specified time [Unix only]\n    getsignal(sig) -- get the signal action for a given signal\n    default_int_handler(action) -- default SIGINT handler (done, but acts string)\n\n    Constants:\n\n    SIG_DFL -- used to refer to the system default handler\n    SIG_IGN -- used to ignore the signal\n    NSIG -- number of defined signals\n\n    SIGINT, SIGTERM, etc. -- signal numbers\n\n    *** IMPORTANT NOTICES ***\n    A signal handler function is called with two arguments:\n    the first is the signal number, the second is the interrupted stack frame.\n\n    According to http://java.sun.com/products/jdk/faq/faq-sun-packages.html\n    'writing java programs that rely on sun.* is risky: they are not portable, and are not supported.'\n\n    However, in Jython, like Python, we let you decide what makes\n    sense for your application. If sun.misc.Signal is not available,\n    an ImportError is raised.\n"));
      var1.setline(30);
      PyString.fromInterned("\n    This module provides mechanisms to use signal handlers in Python.\n\n    Functions:\n\n    signal(sig,action) -- set the action for a given signal (done)\n    pause(sig) -- wait until a signal arrives [Unix only]\n    alarm(seconds) -- cause SIGALRM after a specified time [Unix only]\n    getsignal(sig) -- get the signal action for a given signal\n    default_int_handler(action) -- default SIGINT handler (done, but acts string)\n\n    Constants:\n\n    SIG_DFL -- used to refer to the system default handler\n    SIG_IGN -- used to ignore the signal\n    NSIG -- number of defined signals\n\n    SIGINT, SIGTERM, etc. -- signal numbers\n\n    *** IMPORTANT NOTICES ***\n    A signal handler function is called with two arguments:\n    the first is the signal number, the second is the interrupted stack frame.\n\n    According to http://java.sun.com/products/jdk/faq/faq-sun-packages.html\n    'writing java programs that rely on sun.* is risky: they are not portable, and are not supported.'\n\n    However, in Jython, like Python, we let you decide what makes\n    sense for your application. If sun.misc.Signal is not available,\n    an ImportError is raised.\n");
      var1.setline(32);
      String[] var3 = new String[]{"SecurityException"};
      PyObject[] var7 = imp.importFrom("java.lang", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("SecurityException", var4);
      var4 = null;

      PyException var8;
      PyObject var9;
      try {
         var1.setline(34);
         var9 = imp.importOne("sun.misc.Signal", var1, -1);
         var1.setlocal("sun", var9);
         var3 = null;
      } catch (Throwable var5) {
         var8 = Py.setException(var5, var1);
         if (var8.match(var1.getname("ImportError"))) {
            var1.setline(36);
            throw Py.makeException(var1.getname("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("signal module requires sun.misc.Signal, which is not available on this platform")));
         }

         if (var8.match(var1.getname("SecurityException"))) {
            var4 = var8.value;
            var1.setlocal("ex", var4);
            var4 = null;
            var1.setline(38);
            throw Py.makeException(var1.getname("ImportError").__call__(var2, PyString.fromInterned("signal module requires sun.misc.Signal, which is not allowed by your security profile: %s")._mod(var1.getname("ex"))));
         }

         throw var8;
      }

      var1.setline(40);
      var9 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var9);
      var3 = null;
      var1.setline(41);
      var9 = imp.importOne("sun.misc.SignalHandler", var1, -1);
      var1.setlocal("sun", var9);
      var3 = null;
      var1.setline(42);
      var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(43);
      var9 = imp.importOne("threading", var1, -1);
      var1.setlocal("threading", var9);
      var3 = null;
      var1.setline(44);
      var9 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var9);
      var3 = null;
      var1.setline(45);
      var3 = new String[]{"RuntimeException"};
      var7 = imp.importFrom("java.lang", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("RuntimeException", var4);
      var4 = null;
      var1.setline(46);
      var3 = new String[]{"AtomicReference"};
      var7 = imp.importFrom("java.util.concurrent.atomic", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("AtomicReference", var4);
      var4 = null;
      var1.setline(48);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal("debug", var10);
      var3 = null;
      var1.setline(50);
      var7 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var7, _init_signals$1, (PyObject)null);
      var1.setlocal("_init_signals", var11);
      var3 = null;
      var1.setline(105);
      var9 = var1.getname("_init_signals").__call__(var2);
      var1.setlocal("_signals", var9);
      var3 = null;
      var1.setline(106);
      var9 = var1.getname("max").__call__(var2, var1.getname("_signals").__getattr__("iterkeys").__call__(var2))._add(Py.newInteger(1));
      var1.setlocal("NSIG", var9);
      var3 = null;
      var1.setline(107);
      var9 = var1.getname("sun").__getattr__("misc").__getattr__("SignalHandler").__getattr__("SIG_DFL");
      var1.setlocal("SIG_DFL", var9);
      var3 = null;
      var1.setline(108);
      var9 = var1.getname("sun").__getattr__("misc").__getattr__("SignalHandler").__getattr__("SIG_IGN");
      var1.setlocal("SIG_IGN", var9);
      var3 = null;
      var1.setline(110);
      var7 = new PyObject[]{var1.getname("sun").__getattr__("misc").__getattr__("SignalHandler")};
      var4 = Py.makeClass("JythonSignalHandler", var7, JythonSignalHandler$2);
      var1.setlocal("JythonSignalHandler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(119);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, signal$5, PyString.fromInterned("\n    signal(sig, action) -> action\n\n    Set the action for the given signal.  The action can be SIG_DFL,\n    SIG_IGN, or a callable Python object.  The previous action is\n    returned.  See getsignal() for possible return values.\n\n    *** IMPORTANT NOTICE ***\n    A signal handler function is called with two arguments:\n    the first is the signal number, the second is the interrupted stack frame.\n    "));
      var1.setlocal("signal", var11);
      var3 = null;
      var1.setline(151);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, _register_signal$6, (PyObject)null);
      var1.setlocal("_register_signal", var11);
      var3 = null;
      var1.setline(159);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, getsignal$7, PyString.fromInterned("getsignal(sig) -> action\n\n    Return the current action for the given signal.  The return value can be:\n    SIG_IGN -- if the signal is being ignored\n    SIG_DFL -- if the default action for the signal is in effect\n    None -- if an unknown handler is in effect\n    anything else -- the callable Python object used as a handler\n\n    Note for Jython: this function is NOT threadsafe. The underlying\n    Java support only enables getting the current signal handler by\n    setting a new one. So this is completely prone to race conditions.\n    "));
      var1.setlocal("getsignal", var11);
      var3 = null;
      var1.setline(184);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, default_int_handler$8, PyString.fromInterned("\n    default_int_handler(...)\n\n    The default handler for SIGINT installed by Python.\n    It raises KeyboardInterrupt.\n    "));
      var1.setlocal("default_int_handler", var11);
      var3 = null;
      var1.setline(193);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, pause$9, (PyObject)null);
      var1.setlocal("pause", var11);
      var3 = null;
      var1.setline(196);
      var9 = var1.getname("AtomicReference").__call__(var2);
      var1.setlocal("_alarm_timer_holder", var9);
      var3 = null;
      var1.setline(198);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, _alarm_handler$10, (PyObject)null);
      var1.setlocal("_alarm_handler", var11);
      var3 = null;

      try {
         var1.setline(205);
         var1.getname("SIGALRM");
         var1.setline(206);
         var1.getname("signal").__call__(var2, var1.getname("SIGALRM"), var1.getname("_alarm_handler"));
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (!var8.match(var1.getname("NameError"))) {
            throw var8;
         }

         var1.setline(208);
      }

      var1.setline(210);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_Alarm", var7, _Alarm$11);
      var1.setlocal("_Alarm", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(229);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, alarm$15, (PyObject)null);
      var1.setlocal("alarm", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_signals$1(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = PyString.fromInterned("\n        SIGABRT\n        SIGALRM\n        SIGBUS\n        SIGCHLD\n        SIGCONT\n        SIGFPE\n        SIGHUP\n        SIGILL\n        SIGINFO\n        SIGINT\n        SIGIOT\n        SIGKILL\n        SIGPIPE\n        SIGPOLL\n        SIGPROF\n        SIGQUIT\n        SIGSEGV\n        SIGSTOP\n        SIGSYS\n        SIGTERM\n        SIGTRAP\n        SIGTSTP\n        SIGTTIN\n        SIGTTOU\n        SIGURG\n        SIGUSR1\n        SIGUSR2\n        SIGVTALRM\n        SIGWINCH\n        SIGXCPU\n        SIGXFSZ\n    ").__getattr__("split").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getglobal("__import__").__call__(var2, var1.getglobal("__name__"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(91);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(92);
      var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(93);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(93);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(103);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);

         PyException var5;
         PyObject var8;
         try {
            var1.setline(95);
            var8 = var1.getglobal("sun").__getattr__("misc").__getattr__("Signal").__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null));
            var1.setlocal(5, var8);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("RuntimeException"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(99);
         var8 = var1.getlocal(5).__getattr__("getNumber").__call__(var2);
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(100);
         var8 = var1.getlocal(5);
         var1.getlocal(2).__setitem__(var1.getlocal(6), var8);
         var5 = null;
         var1.setline(101);
         var8 = var1.getlocal(5);
         var1.getlocal(3).__setitem__(var1.getlocal(4), var8);
         var5 = null;
         var1.setline(102);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(6));
      }
   }

   public PyObject JythonSignalHandler$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(111);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle$4, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("action", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle$4(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      var1.getlocal(0).__getattr__("action").__call__(var2, var1.getlocal(1).__getattr__("getNumber").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject signal$5(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyString.fromInterned("\n    signal(sig, action) -> action\n\n    Set the action for the given signal.  The action can be SIG_DFL,\n    SIG_IGN, or a callable Python object.  The previous action is\n    returned.  See getsignal() for possible return values.\n\n    *** IMPORTANT NOTICE ***\n    A signal handler function is called with two arguments:\n    the first is the signal number, the second is the interrupted stack frame.\n    ");

      PyException var3;
      PyObject var5;
      try {
         var1.setline(134);
         var5 = var1.getglobal("_signals").__getitem__(var1.getlocal(0));
         var1.setlocal(2, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(136);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("signal number out of range")));
         }

         throw var3;
      }

      var1.setline(138);
      if (var1.getglobal("callable").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(139);
         var5 = var1.getglobal("_register_signal").__call__(var2, var1.getlocal(2), var1.getglobal("JythonSignalHandler").__call__(var2, var1.getlocal(1)));
         var1.setlocal(3, var5);
         var3 = null;
      } else {
         var1.setline(140);
         var5 = var1.getlocal(1);
         PyObject var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("SIG_IGN"), var1.getglobal("SIG_DFL")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("sun").__getattr__("misc").__getattr__("SignalHandler"));
         }

         if (!var10000.__nonzero__()) {
            var1.setline(143);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("signal handler must be signal.SIG_IGN, signal.SIG_DFL, or a callable object")));
         }

         var1.setline(141);
         var5 = var1.getglobal("_register_signal").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(145);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("JythonSignalHandler")).__nonzero__()) {
         var1.setline(146);
         var5 = var1.getlocal(3).__getattr__("action");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(148);
         var5 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _register_signal$6(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(153);
         PyObject var3 = var1.getglobal("sun").__getattr__("misc").__getattr__("Signal").__getattr__("handle").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("RuntimeException"))) {
            PyObject var5 = var4.value;
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(155);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(2).__getattr__("getMessage").__call__(var2)));
         } else {
            throw var4;
         }
      }
   }

   public PyObject getsignal$7(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyString.fromInterned("getsignal(sig) -> action\n\n    Return the current action for the given signal.  The return value can be:\n    SIG_IGN -- if the signal is being ignored\n    SIG_DFL -- if the default action for the signal is in effect\n    None -- if an unknown handler is in effect\n    anything else -- the callable Python object used as a handler\n\n    Note for Jython: this function is NOT threadsafe. The underlying\n    Java support only enables getting the current signal handler by\n    setting a new one. So this is completely prone to race conditions.\n    ");

      PyException var3;
      PyObject var5;
      try {
         var1.setline(173);
         var5 = var1.getglobal("_signals").__getitem__(var1.getlocal(0));
         var1.setlocal(1, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(175);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("signal number out of range")));
         }

         throw var3;
      }

      var1.setline(176);
      var5 = var1.getglobal("_register_signal").__call__(var2, var1.getlocal(1), var1.getglobal("SIG_DFL"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(177);
      var1.getglobal("_register_signal").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(179);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("JythonSignalHandler")).__nonzero__()) {
         var1.setline(180);
         var5 = var1.getlocal(2).__getattr__("action");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(182);
         var5 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject default_int_handler$8(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyString.fromInterned("\n    default_int_handler(...)\n\n    The default handler for SIGINT installed by Python.\n    It raises KeyboardInterrupt.\n    ");
      var1.setline(191);
      throw Py.makeException(var1.getglobal("KeyboardInterrupt"));
   }

   public PyObject pause$9(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject _alarm_handler$10(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      Py.println(PyString.fromInterned("Alarm clock"));
      var1.setline(200);
      var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Alarm$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(211);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start$13, (PyObject)null);
      var1.setlocal("start", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cancel$14, (PyObject)null);
      var1.setlocal("cancel", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("interval", var3);
      var3 = null;
      var1.setline(213);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("task", var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("scheduled", var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getglobal("threading").__getattr__("Timer").__call__(var2, var1.getlocal(0).__getattr__("interval"), var1.getlocal(0).__getattr__("task"));
      var1.getlocal(0).__setattr__("timer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start$13(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      var1.getlocal(0).__getattr__("timer").__getattr__("start").__call__(var2);
      var1.setline(219);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2)._add(var1.getlocal(0).__getattr__("interval"));
      var1.getlocal(0).__setattr__("scheduled", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cancel$14(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      var1.getlocal(0).__getattr__("timer").__getattr__("cancel").__call__(var2);
      var1.setline(223);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(224);
      PyObject var10000 = var1.getlocal(0).__getattr__("scheduled");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("scheduled");
         var10000 = var3._gt(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(225);
         var3 = var1.getlocal(0).__getattr__("scheduled")._sub(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(227);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject alarm$15(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(231);
         var1.getglobal("SIGALRM");
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("NameError"))) {
            var1.setline(233);
            throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("alarm not implemented on this platform")));
         }

         throw var3;
      }

      var1.setline(235);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, raise_alarm$16, (PyObject)null);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(241);
      PyObject var7 = var1.getlocal(0);
      PyObject var10000 = var7._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(242);
         var7 = var1.getglobal("_Alarm").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
      } else {
         var1.setline(244);
         var7 = var1.getglobal("None");
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(245);
      var7 = var1.getglobal("_alarm_timer_holder").__getattr__("getAndSet").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(246);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(247);
         var7 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getattr__("cancel").__call__(var2));
         var1.setlocal(4, var7);
         var3 = null;
      } else {
         var1.setline(249);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(4, var8);
         var3 = null;
      }

      var1.setline(251);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(252);
         var1.getlocal(2).__getattr__("start").__call__(var2);
      }

      var1.setline(253);
      var7 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject raise_alarm$16(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(237);
         var1.getglobal("sun").__getattr__("misc").__getattr__("Signal").__getattr__("raise").__call__(var2, var1.getglobal("_signals").__getitem__(var1.getglobal("SIGALRM")));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("RuntimeException"))) {
            PyObject var4 = var3.value;
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(239);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(0).__getattr__("getMessage").__call__(var2)));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public signal$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"possible_signals", "_module", "signals", "signals_by_name", "signal_name", "java_signal", "signal_number"};
      _init_signals$1 = Py.newCode(0, var2, var1, "_init_signals", 50, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JythonSignalHandler$2 = Py.newCode(0, var2, var1, "JythonSignalHandler", 110, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "action"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 111, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "signal"};
      handle$4 = Py.newCode(2, var2, var1, "handle", 114, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sig", "action", "signal", "prev"};
      signal$5 = Py.newCode(2, var2, var1, "signal", 119, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"signal", "action", "err"};
      _register_signal$6 = Py.newCode(2, var2, var1, "_register_signal", 151, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sig", "signal", "current"};
      getsignal$7 = Py.newCode(1, var2, var1, "getsignal", 159, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sig", "frame"};
      default_int_handler$8 = Py.newCode(2, var2, var1, "default_int_handler", 184, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      pause$9 = Py.newCode(0, var2, var1, "pause", 193, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sig", "frame"};
      _alarm_handler$10 = Py.newCode(2, var2, var1, "_alarm_handler", 198, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Alarm$11 = Py.newCode(0, var2, var1, "_Alarm", 210, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "interval", "task"};
      __init__$12 = Py.newCode(3, var2, var1, "__init__", 211, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      start$13 = Py.newCode(1, var2, var1, "start", 217, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      cancel$14 = Py.newCode(1, var2, var1, "cancel", 221, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"time", "raise_alarm", "new_alarm_timer", "old_alarm_timer", "scheduled"};
      alarm$15 = Py.newCode(1, var2, var1, "alarm", 229, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"err"};
      raise_alarm$16 = Py.newCode(0, var2, var1, "raise_alarm", 235, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new signal$py("signal$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(signal$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._init_signals$1(var2, var3);
         case 2:
            return this.JythonSignalHandler$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.handle$4(var2, var3);
         case 5:
            return this.signal$5(var2, var3);
         case 6:
            return this._register_signal$6(var2, var3);
         case 7:
            return this.getsignal$7(var2, var3);
         case 8:
            return this.default_int_handler$8(var2, var3);
         case 9:
            return this.pause$9(var2, var3);
         case 10:
            return this._alarm_handler$10(var2, var3);
         case 11:
            return this._Alarm$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.start$13(var2, var3);
         case 14:
            return this.cancel$14(var2, var3);
         case 15:
            return this.alarm$15(var2, var3);
         case 16:
            return this.raise_alarm$16(var2, var3);
         default:
            return null;
      }
   }
}
