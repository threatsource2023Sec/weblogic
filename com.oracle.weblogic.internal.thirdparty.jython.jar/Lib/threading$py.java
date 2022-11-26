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
@MTime(1498849384000L)
@Filename("threading.py")
public class threading$py extends PyFunctionTable implements PyRunnable {
   static threading$py self;
   static final PyCode f$0;
   static final PyCode _Verbose$1;
   static final PyCode __init__$2;
   static final PyCode _note$3;
   static final PyCode _Verbose$4;
   static final PyCode __init__$5;
   static final PyCode _note$6;
   static final PyCode setprofile$7;
   static final PyCode settrace$8;
   static final PyCode Semaphore$9;
   static final PyCode __init__$10;
   static final PyCode acquire$11;
   static final PyCode __enter__$12;
   static final PyCode release$13;
   static final PyCode __exit__$14;
   static final PyCode JavaThread$15;
   static final PyCode __init__$16;
   static final PyCode __repr__$17;
   static final PyCode __eq__$18;
   static final PyCode __ne__$19;
   static final PyCode start$20;
   static final PyCode run$21;
   static final PyCode join$22;
   static final PyCode ident$23;
   static final PyCode getName$24;
   static final PyCode setName$25;
   static final PyCode isAlive$26;
   static final PyCode isDaemon$27;
   static final PyCode setDaemon$28;
   static final PyCode __tojava__$29;
   static final PyCode _register_thread$30;
   static final PyCode _unregister_thread$31;
   static final PyCode Thread$32;
   static final PyCode __init__$33;
   static final PyCode _create_thread$34;
   static final PyCode run$35;
   static final PyCode _Thread__bootstrap$36;
   static final PyCode _Thread__stop$37;
   static final PyCode _Thread__delete$38;
   static final PyCode _MainThread$39;
   static final PyCode __init__$40;
   static final PyCode _create_thread$41;
   static final PyCode _set_daemon$42;
   static final PyCode _MainThread__exitfunc$43;
   static final PyCode _pickSomeNonDaemonThread$44;
   static final PyCode currentThread$45;
   static final PyCode activeCount$46;
   static final PyCode enumerate$47;
   static final PyCode Timer$48;
   static final PyCode _Timer$49;
   static final PyCode __init__$50;
   static final PyCode cancel$51;
   static final PyCode run$52;
   static final PyCode _Semaphore$53;
   static final PyCode __init__$54;
   static final PyCode acquire$55;
   static final PyCode release$56;
   static final PyCode BoundedSemaphore$57;
   static final PyCode _BoundedSemaphore$58;
   static final PyCode __init__$59;
   static final PyCode __enter__$60;
   static final PyCode release$61;
   static final PyCode __exit__$62;
   static final PyCode Event$63;
   static final PyCode _Event$64;
   static final PyCode __init__$65;
   static final PyCode isSet$66;
   static final PyCode set$67;
   static final PyCode clear$68;
   static final PyCode wait$69;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"IllegalThreadStateException", "InterruptedException"};
      PyObject[] var5 = imp.importFrom("java.lang", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("IllegalThreadStateException", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("InterruptedException", var4);
      var4 = null;
      var1.setline(2);
      var3 = new String[]{"Collections", "WeakHashMap"};
      var5 = imp.importFrom("java.util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Collections", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("WeakHashMap", var4);
      var4 = null;
      var1.setline(3);
      var3 = new String[]{"Semaphore", "CyclicBarrier"};
      var5 = imp.importFrom("java.util.concurrent", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Semaphore", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CyclicBarrier", var4);
      var4 = null;
      var1.setline(4);
      var3 = new String[]{"ReentrantLock"};
      var5 = imp.importFrom("java.util.concurrent.locks", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("ReentrantLock", var4);
      var4 = null;
      var1.setline(5);
      var3 = new String[]{"jython"};
      var5 = imp.importFrom("org.python.util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("jython", var4);
      var4 = null;
      var1.setline(6);
      var3 = new String[]{"Py"};
      var5 = imp.importFrom("org.python.core", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Py", var4);
      var4 = null;
      var1.setline(7);
      var3 = new String[]{"CacheBuilder", "CacheLoader", "MapMaker", "dict_builder"};
      var5 = imp.importFrom("jythonlib", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("CacheBuilder", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CacheLoader", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("MapMaker", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("dict_builder", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"_newFunctionThread"};
      var5 = imp.importFrom("thread", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_newFunctionThread", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"_local"};
      var5 = imp.importFrom("thread", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("local", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"Lock", "RLock", "Condition", "_Lock", "_RLock"};
      var5 = imp.importFrom("_threading", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Lock", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("RLock", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Condition", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("_Lock", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("_RLock", var4);
      var4 = null;
      var1.setline(11);
      PyObject var6 = imp.importOne("java.lang.Thread", var1, -1);
      var1.setlocal("java", var6);
      var3 = null;
      var1.setline(12);
      var6 = imp.importOneAs("sys", var1, -1);
      var1.setlocal("_sys", var6);
      var3 = null;
      var1.setline(13);
      var3 = new String[]{"print_exc"};
      var5 = imp.importFrom("traceback", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_print_exc", var4);
      var4 = null;
      var1.setline(17);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("activeCount"), PyString.fromInterned("active_count"), PyString.fromInterned("Condition"), PyString.fromInterned("currentThread"), PyString.fromInterned("current_thread"), PyString.fromInterned("enumerate"), PyString.fromInterned("Event"), PyString.fromInterned("Lock"), PyString.fromInterned("RLock"), PyString.fromInterned("Semaphore"), PyString.fromInterned("BoundedSemaphore"), PyString.fromInterned("Thread"), PyString.fromInterned("Timer"), PyString.fromInterned("setprofile"), PyString.fromInterned("settrace"), PyString.fromInterned("local"), PyString.fromInterned("stack_size")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(22);
      var6 = var1.getname("False");
      var1.setlocal("_VERBOSE", var6);
      var3 = null;
      var1.setline(24);
      if (var1.getname("__debug__").__nonzero__()) {
         var1.setline(26);
         var5 = new PyObject[]{var1.getname("object")};
         var4 = Py.makeClass("_Verbose", var5, _Verbose$1);
         var1.setlocal("_Verbose", var4);
         var4 = null;
         Arrays.fill(var5, (Object)null);
      } else {
         var1.setline(42);
         var5 = new PyObject[]{var1.getname("object")};
         var4 = Py.makeClass("_Verbose", var5, _Verbose$4);
         var1.setlocal("_Verbose", var4);
         var4 = null;
         Arrays.fill(var5, (Object)null);
      }

      var1.setline(50);
      var6 = var1.getname("None");
      var1.setlocal("_profile_hook", var6);
      var3 = null;
      var1.setline(51);
      var6 = var1.getname("None");
      var1.setlocal("_trace_hook", var6);
      var3 = null;
      var1.setline(53);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, setprofile$7, (PyObject)null);
      var1.setlocal("setprofile", var8);
      var3 = null;
      var1.setline(57);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, settrace$8, (PyObject)null);
      var1.setlocal("settrace", var8);
      var3 = null;
      var1.setline(64);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Semaphore", var5, Semaphore$9);
      var1.setlocal("Semaphore", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(88);
      PyDictionary var9 = new PyDictionary(new PyObject[]{var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("NEW"), PyString.fromInterned("initial"), var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("RUNNABLE"), PyString.fromInterned("started"), var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("BLOCKED"), PyString.fromInterned("started"), var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("WAITING"), PyString.fromInterned("started"), var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("TIMED_WAITING"), PyString.fromInterned("started"), var1.getname("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("TERMINATED"), PyString.fromInterned("stopped")});
      var1.setlocal("ThreadStates", var9);
      var3 = null;
      var1.setline(97);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("JavaThread", var5, JavaThread$15);
      var1.setlocal("JavaThread", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(182);
      var6 = var1.getname("dict_builder").__call__(var2, var1.getname("MapMaker").__call__(var2).__getattr__("weakValues").__call__(var2).__getattr__("makeMap")).__call__(var2);
      var1.setlocal("_threads", var6);
      var3 = null;
      var1.setline(183);
      var6 = var1.getname("_threads");
      var1.setlocal("_active", var6);
      var3 = null;
      var1.setline(185);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _register_thread$30, (PyObject)null);
      var1.setlocal("_register_thread", var8);
      var3 = null;
      var1.setline(188);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _unregister_thread$31, (PyObject)null);
      var1.setlocal("_unregister_thread", var8);
      var3 = null;
      var1.setline(193);
      var5 = new PyObject[]{var1.getname("JavaThread")};
      var4 = Py.makeClass("Thread", var5, Thread$32);
      var1.setlocal("Thread", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(275);
      var5 = new PyObject[]{var1.getname("Thread")};
      var4 = Py.makeClass("_MainThread", var5, _MainThread$39);
      var1.setlocal("_MainThread", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(300);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _pickSomeNonDaemonThread$44, (PyObject)null);
      var1.setlocal("_pickSomeNonDaemonThread", var8);
      var3 = null;
      var1.setline(306);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, currentThread$45, (PyObject)null);
      var1.setlocal("currentThread", var8);
      var3 = null;
      var1.setline(313);
      var6 = var1.getname("currentThread");
      var1.setlocal("current_thread", var6);
      var3 = null;
      var1.setline(315);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, activeCount$46, (PyObject)null);
      var1.setlocal("activeCount", var8);
      var3 = null;
      var1.setline(318);
      var6 = var1.getname("activeCount");
      var1.setlocal("active_count", var6);
      var3 = null;
      var1.setline(320);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, enumerate$47, (PyObject)null);
      var1.setlocal("enumerate", var8);
      var3 = null;
      var1.setline(323);
      var3 = new String[]{"stack_size"};
      var5 = imp.importFrom("thread", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("stack_size", var4);
      var4 = null;
      var1.setline(326);
      var1.getname("_MainThread").__call__(var2);
      var1.setline(334);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, Timer$48, (PyObject)null);
      var1.setlocal("Timer", var8);
      var3 = null;
      var1.setline(337);
      var5 = new PyObject[]{var1.getname("Thread")};
      var4 = Py.makeClass("_Timer", var5, _Timer$49);
      var1.setlocal("_Timer", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(365);
      var5 = new PyObject[]{var1.getname("_Verbose")};
      var4 = Py.makeClass("_Semaphore", var5, _Semaphore$53);
      var1.setlocal("_Semaphore", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(405);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, BoundedSemaphore$57, (PyObject)null);
      var1.setlocal("BoundedSemaphore", var8);
      var3 = null;
      var1.setline(408);
      var5 = new PyObject[]{var1.getname("_Semaphore")};
      var4 = Py.makeClass("_BoundedSemaphore", var5, _BoundedSemaphore$58);
      var1.setlocal("_BoundedSemaphore", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(427);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, Event$63, (PyObject)null);
      var1.setlocal("Event", var8);
      var3 = null;
      var1.setline(430);
      var5 = new PyObject[]{var1.getname("_Verbose")};
      var4 = Py.makeClass("_Event", var5, _Event$64);
      var1.setlocal("_Event", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Verbose$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _note$3, (PyObject)null);
      var1.setlocal("_note", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var3 = var1.getglobal("_VERBOSE");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(31);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Verbose__verbose", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _note$3(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      if (var1.getlocal(0).__getattr__("_Verbose__verbose").__nonzero__()) {
         var1.setline(35);
         PyObject var3 = var1.getlocal(1)._mod(var1.getlocal(2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(36);
         var3 = PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("currentThread").__call__(var2).__getattr__("getName").__call__(var2), var1.getlocal(1)}));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(38);
         var1.getglobal("_sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Verbose$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _note$6, (PyObject)null);
      var1.setlocal("_note", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _note$6(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setprofile$7(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(0);
      var1.setglobal("_profile_hook", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject settrace$8(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0);
      var1.setglobal("_trace_hook", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Semaphore$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(65);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(70);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, acquire$11, (PyObject)null);
      var1.setlocal("acquire", var4);
      var3 = null;
      var1.setline(77);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$12, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, release$13, (PyObject)null);
      var1.setlocal("release", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$14, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Semaphore initial value must be >= 0")));
      } else {
         var1.setline(68);
         var3 = var1.getglobal("java").__getattr__("util").__getattr__("concurrent").__getattr__("Semaphore").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("_semaphore", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject acquire$11(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(72);
         var1.getlocal(0).__getattr__("_semaphore").__getattr__("acquire").__call__(var2);
         var1.setline(73);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(75);
         var3 = var1.getlocal(0).__getattr__("_semaphore").__getattr__("tryAcquire").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __enter__$12(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      var1.setline(79);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject release$13(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      var1.getlocal(0).__getattr__("_semaphore").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __exit__$14(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JavaThread$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(98);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$17, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$18, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$19, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start$20, (PyObject)null);
      var1.setlocal("start", var4);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$21, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(126);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, join$22, (PyObject)null);
      var1.setlocal("join", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ident$23, (PyObject)null);
      var1.setlocal("ident", var4);
      var3 = null;
      var1.setline(142);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("ident"));
      var1.setlocal("ident", var5);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getName$24, (PyObject)null);
      var1.setlocal("getName", var4);
      var3 = null;
      var1.setline(147);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setName$25, (PyObject)null);
      var1.setlocal("setName", var4);
      var3 = null;
      var1.setline(150);
      var5 = var1.getname("property").__call__(var2, var1.getname("getName"), var1.getname("setName"));
      var1.setlocal("name", var5);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isAlive$26, (PyObject)null);
      var1.setlocal("isAlive", var4);
      var3 = null;
      var1.setline(155);
      var5 = var1.getname("isAlive");
      var1.setlocal("is_alive", var5);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isDaemon$27, (PyObject)null);
      var1.setlocal("isDaemon", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDaemon$28, (PyObject)null);
      var1.setlocal("setDaemon", var4);
      var3 = null;
      var1.setline(172);
      var5 = var1.getname("property").__call__(var2, var1.getname("isDaemon"), var1.getname("setDaemon"));
      var1.setlocal("daemon", var5);
      var3 = null;
      var1.setline(174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __tojava__$29, (PyObject)null);
      var1.setlocal("__tojava__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_thread", var3);
      var3 = null;
      var1.setline(100);
      var1.getglobal("_register_thread").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$17(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getglobal("ThreadStates").__getitem__(var1.getlocal(1).__getattr__("getState").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(105);
      if (var1.getlocal(1).__getattr__("isDaemon").__call__(var2).__nonzero__()) {
         var1.setline(105);
         var1.getlocal(2)._add(PyString.fromInterned(" daemon"));
      }

      var1.setline(106);
      var3 = PyString.fromInterned("<%s(%s, %s %s)>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("getName").__call__(var2), var1.getlocal(2), var1.getlocal(0).__getattr__("ident")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$18(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("JavaThread")).__nonzero__()) {
         var1.setline(110);
         var3 = var1.getlocal(0).__getattr__("_thread");
         PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("_thread"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(112);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$19(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(0).__getattr__("__eq__").__call__(var2, var1.getlocal(1)).__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject start$20(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(119);
         var1.getlocal(0).__getattr__("_thread").__getattr__("start").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("IllegalThreadStateException"))) {
            var1.setline(121);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("threads can only be started once")));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$21(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      var1.getlocal(0).__getattr__("_thread").__getattr__("run").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject join$22(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread");
      PyObject var10000 = var3._eq(var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("currentThread").__call__(var2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(128);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot join current thread")));
      } else {
         var1.setline(129);
         var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("getState").__call__(var2);
         var10000 = var3._eq(var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("NEW"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(130);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot join thread before it is started")));
         } else {
            var1.setline(131);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(132);
               var3 = var1.getlocal(1)._mul(Py.newFloat(1000.0));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(133);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(134);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2)._sub(var1.getlocal(3))._mul(Py.newFloat(1000000.0)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(135);
               var1.getlocal(0).__getattr__("_thread").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            } else {
               var1.setline(137);
               var1.getlocal(0).__getattr__("_thread").__getattr__("join").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject ident$23(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("getId").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getName$24(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("getName").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setName$25(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      var1.getlocal(0).__getattr__("_thread").__getattr__("setName").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isAlive$26(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("isAlive").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isDaemon$27(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("isDaemon").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setDaemon$28(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyObject var3 = var1.getlocal(0).__getattr__("_thread").__getattr__("getState").__call__(var2);
      PyObject var10000 = var3._ne(var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("State").__getattr__("NEW"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot set daemon status of active thread")));
      } else {
         try {
            var1.setline(165);
            var1.getlocal(0).__getattr__("_thread").__getattr__("setDaemon").__call__(var2, var1.getglobal("bool").__call__(var2, var1.getlocal(1)));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("IllegalThreadStateException"))) {
               var1.setline(170);
               throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot set daemon status of active thread")));
            }

            throw var5;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __tojava__$29(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_thread"), var1.getlocal(1)).__nonzero__()) {
         var1.setline(176);
         var3 = var1.getlocal(0).__getattr__("_thread");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(177);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__()) {
            var1.setline(178);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(179);
            var3 = var1.getglobal("Py").__getattr__("NoConversion");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _register_thread$30(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("_threads").__setitem__(var1.getlocal(0).__getattr__("getId").__call__(var2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unregister_thread$31(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      var1.getglobal("_threads").__getattr__("pop").__call__(var2, var1.getlocal(0).__getattr__("getId").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Thread$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(194);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$33, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_thread$34, (PyObject)null);
      var1.setlocal("_create_thread", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$35, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Thread__bootstrap$36, (PyObject)null);
      var1.setlocal("_Thread__bootstrap", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Thread__stop$37, (PyObject)null);
      var1.setlocal("_Thread__stop", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Thread__delete$38, (PyObject)null);
      var1.setlocal("_Thread__delete", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("group argument must be None for now"));
         }
      }

      var1.setline(196);
      var3 = var1.getlocal(0).__getattr__("_create_thread").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(197);
      var1.getglobal("JavaThread").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(6));
      var1.setline(198);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(199);
         PyTuple var4 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(200);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(201);
         PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(5, var5);
         var3 = null;
      }

      var1.setline(202);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_target", var3);
      var3 = null;
      var1.setline(203);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_args", var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_kwargs", var3);
      var3 = null;
      var1.setline(205);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(206);
         var1.getlocal(0).__getattr__("_thread").__getattr__("setName").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_thread$34(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getglobal("_newFunctionThread").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_Thread__bootstrap"), (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$35(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      if (var1.getlocal(0).__getattr__("_target").__nonzero__()) {
         var1.setline(213);
         PyObject var10000 = var1.getlocal(0).__getattr__("_target");
         PyObject[] var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, var1.getlocal(0).__getattr__("_args"), var1.getlocal(0).__getattr__("_kwargs"));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Thread__bootstrap$36(PyFrame var1, ThreadState var2) {
      Object var3 = null;

      try {
         var1.setline(217);
         if (var1.getglobal("_trace_hook").__nonzero__()) {
            var1.setline(218);
            var1.getglobal("_sys").__getattr__("settrace").__call__(var2, var1.getglobal("_trace_hook"));
         }

         var1.setline(219);
         if (var1.getglobal("_profile_hook").__nonzero__()) {
            var1.setline(220);
            var1.getglobal("_sys").__getattr__("setprofile").__call__(var2, var1.getglobal("_profile_hook"));
         }

         try {
            var1.setline(222);
            var1.getlocal(0).__getattr__("run").__call__(var2);
         } catch (Throwable var11) {
            PyException var4 = Py.setException(var11, var1);
            if (var4.match(var1.getglobal("SystemExit"))) {
               var1.setline(224);
            } else if (var4.match(var1.getglobal("InterruptedException"))) {
               var1.setline(228);
               if (var1.getglobal("jython").__getattr__("shouldRestart").__not__().__nonzero__()) {
                  var1.setline(229);
                  throw Py.makeException();
               }
            } else {
               var1.setline(234);
               PyObject[] var5;
               String[] var6;
               if (var1.getglobal("_sys").__nonzero__()) {
                  var1.setline(235);
                  var1.getglobal("_sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("Exception in thread %s:")._mod(var1.getlocal(0).__getattr__("getName").__call__(var2)));
                  var1.setline(237);
                  PyObject var10000 = var1.getglobal("_print_exc");
                  var5 = new PyObject[]{var1.getglobal("_sys").__getattr__("stderr")};
                  var6 = new String[]{"file"};
                  var10000.__call__(var2, var5, var6);
                  var5 = null;
               } else {
                  var1.setline(241);
                  PyObject var13 = var1.getlocal(0).__getattr__("_Thread__exc_info").__call__(var2);
                  PyObject[] var14 = Py.unpackSequence(var13, 3);
                  PyObject var7 = var14[0];
                  var1.setlocal(1, var7);
                  var7 = null;
                  var7 = var14[1];
                  var1.setlocal(2, var7);
                  var7 = null;
                  var7 = var14[2];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var5 = null;
                  var5 = null;

                  try {
                     var1.setline(243);
                     PyObject var15 = var1.getlocal(0).__getattr__("_Thread__stderr");
                     Py.println(var15, PyString.fromInterned("Exception in thread ")._add(var1.getlocal(0).__getattr__("getName").__call__(var2))._add(PyString.fromInterned(" (most likely raised during interpreter shutdown):")));
                     var1.setline(246);
                     var15 = var1.getlocal(0).__getattr__("_Thread__stderr");
                     Py.println(var15, PyString.fromInterned("Traceback (most recent call last):"));

                     while(true) {
                        var1.setline(248);
                        if (!var1.getlocal(3).__nonzero__()) {
                           var1.setline(255);
                           var15 = var1.getlocal(0).__getattr__("_Thread__stderr");
                           Py.println(var15, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
                           break;
                        }

                        var1.setline(249);
                        var15 = var1.getlocal(0).__getattr__("_Thread__stderr");
                        Py.println(var15, PyString.fromInterned("  File \"%s\", line %s, in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("tb_frame").__getattr__("f_code").__getattr__("co_filename"), var1.getlocal(3).__getattr__("tb_lineno"), var1.getlocal(3).__getattr__("tb_frame").__getattr__("f_code").__getattr__("co_name")})));
                        var1.setline(254);
                        var15 = var1.getlocal(3).__getattr__("tb_next");
                        var1.setlocal(3, var15);
                        var6 = null;
                     }
                  } catch (Throwable var10) {
                     Py.addTraceback(var10, var1);
                     var1.setline(259);
                     var1.dellocal(1);
                     var1.dellocal(2);
                     var1.dellocal(3);
                     throw (Throwable)var10;
                  }

                  var1.setline(259);
                  var1.dellocal(1);
                  var1.dellocal(2);
                  var1.dellocal(3);
               }
            }
         }
      } catch (Throwable var12) {
         Py.addTraceback(var12, var1);
         var1.setline(262);
         var1.getlocal(0).__getattr__("_Thread__stop").__call__(var2);

         try {
            var1.setline(264);
            var1.getlocal(0).__getattr__("_Thread__delete").__call__(var2);
         } catch (Throwable var8) {
            Py.setException(var8, var1);
            var1.setline(266);
         }

         throw (Throwable)var12;
      }

      var1.setline(262);
      var1.getlocal(0).__getattr__("_Thread__stop").__call__(var2);

      try {
         var1.setline(264);
         var1.getlocal(0).__getattr__("_Thread__delete").__call__(var2);
      } catch (Throwable var9) {
         Py.setException(var9, var1);
         var1.setline(266);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Thread__stop$37(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Thread__delete$38(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      var1.getglobal("_unregister_thread").__call__(var2, var1.getlocal(0).__getattr__("_thread"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _MainThread$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(276);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$40, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_thread$41, (PyObject)null);
      var1.setlocal("_create_thread", var4);
      var3 = null;
      var1.setline(290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_daemon$42, (PyObject)null);
      var1.setlocal("_set_daemon", var4);
      var3 = null;
      var1.setline(293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _MainThread__exitfunc$43, (PyObject)null);
      var1.setlocal("_MainThread__exitfunc", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$40(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyObject var3 = var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("currentThread").__call__(var2).__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("main"));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(280);
         var10000 = var1.getglobal("dict");
         var5 = new PyObject[]{PyString.fromInterned("MainThread")};
         var4 = new String[]{"name"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(282);
         PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(283);
      var10000 = var1.getglobal("Thread").__getattr__("__init__");
      var5 = new PyObject[]{var1.getlocal(0)};
      var4 = new String[0];
      var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var1.setline(284);
      var3 = imp.importOne("atexit", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(285);
      var1.getlocal(2).__getattr__("register").__call__(var2, var1.getlocal(0).__getattr__("_MainThread__exitfunc"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_thread$41(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("currentThread").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_daemon$42(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _MainThread__exitfunc$43(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      var1.getglobal("_unregister_thread").__call__(var2, var1.getlocal(0).__getattr__("_thread"));
      var1.setline(295);
      PyObject var3 = var1.getglobal("_pickSomeNonDaemonThread").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(296);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(297);
         var1.getlocal(1).__getattr__("join").__call__(var2);
         var1.setline(298);
         var3 = var1.getglobal("_pickSomeNonDaemonThread").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject _pickSomeNonDaemonThread$44(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(301);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(304);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(0, var4);
         var1.setline(302);
         var10000 = var1.getlocal(0).__getattr__("isDaemon").__call__(var2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("isAlive").__call__(var2);
         }
      } while(!var10000.__nonzero__());

      var1.setline(303);
      var5 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject currentThread$45(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getglobal("java").__getattr__("lang").__getattr__("Thread").__getattr__("currentThread").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(308);
      var3 = var1.getglobal("_threads").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("getId").__call__(var2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(309);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(310);
         var3 = var1.getglobal("JavaThread").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(311);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject activeCount$46(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("_threads"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject enumerate$47(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyObject var3 = var1.getglobal("_threads").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Timer$48(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyObject var10000 = var1.getglobal("_Timer");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _Timer$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Call a function after a specified number of seconds:\n\n    t = Timer(30.0, f, args=[], kwargs={})\n    t.start()\n    t.cancel() # stop the timer's action if it's still waiting\n    "));
      var1.setline(343);
      PyString.fromInterned("Call a function after a specified number of seconds:\n\n    t = Timer(30.0, f, args=[], kwargs={})\n    t.start()\n    t.cancel() # stop the timer's action if it's still waiting\n    ");
      var1.setline(345);
      PyObject[] var3 = new PyObject[]{new PyList(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cancel$51, PyString.fromInterned("Stop the timer if it hasn't finished yet"));
      var1.setlocal("cancel", var4);
      var3 = null;
      var1.setline(357);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$52, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      var1.getglobal("Thread").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(347);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("interval", var3);
      var3 = null;
      var1.setline(348);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("function", var3);
      var3 = null;
      var1.setline(349);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("args", var3);
      var3 = null;
      var1.setline(350);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("kwargs", var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getglobal("Event").__call__(var2);
      var1.getlocal(0).__setattr__("finished", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cancel$51(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyString.fromInterned("Stop the timer if it hasn't finished yet");
      var1.setline(355);
      var1.getlocal(0).__getattr__("finished").__getattr__("set").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$52(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      var1.getlocal(0).__getattr__("finished").__getattr__("wait").__call__(var2, var1.getlocal(0).__getattr__("interval"));
      var1.setline(359);
      if (var1.getlocal(0).__getattr__("finished").__getattr__("isSet").__call__(var2).__not__().__nonzero__()) {
         var1.setline(360);
         PyObject var10000 = var1.getlocal(0).__getattr__("function");
         PyObject[] var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, var1.getlocal(0).__getattr__("args"), var1.getlocal(0).__getattr__("kwargs"));
         var3 = null;
      }

      var1.setline(361);
      var1.getlocal(0).__getattr__("finished").__getattr__("set").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Semaphore$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(369);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$54, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(376);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, acquire$55, (PyObject)null);
      var1.setlocal("acquire", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, release$56, (PyObject)null);
      var1.setlocal("release", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$54(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Semaphore initial value must be >= 0")));
      } else {
         var1.setline(372);
         var1.getglobal("_Verbose").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setline(373);
         var3 = var1.getglobal("Condition").__call__(var2, var1.getglobal("Lock").__call__(var2));
         var1.getlocal(0).__setattr__("_Semaphore__cond", var3);
         var3 = null;
         var1.setline(374);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_Semaphore__value", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject acquire$55(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(378);
      var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("acquire").__call__(var2);

      while(true) {
         var1.setline(379);
         var3 = var1.getlocal(0).__getattr__("_Semaphore__value");
         PyObject var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(387);
            var3 = var1.getlocal(0).__getattr__("_Semaphore__value")._sub(Py.newInteger(1));
            var1.getlocal(0).__setattr__("_Semaphore__value", var3);
            var3 = null;
            var1.setline(388);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(389);
               var1.getlocal(0).__getattr__("_note").__call__((ThreadState)var2, PyString.fromInterned("%s.acquire: success, value=%s"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(0).__getattr__("_Semaphore__value"));
            }

            var1.setline(391);
            var3 = var1.getglobal("True");
            var1.setlocal(2, var3);
            var3 = null;
            break;
         }

         var1.setline(380);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            break;
         }

         var1.setline(382);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var1.setline(383);
            var1.getlocal(0).__getattr__("_note").__call__(var2, PyString.fromInterned("%s.acquire(%s): blocked waiting, value=%s"), var1.getlocal(0), var1.getlocal(1), var1.getlocal(0).__getattr__("_Semaphore__value"));
         }

         var1.setline(385);
         var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("wait").__call__(var2);
      }

      var1.setline(392);
      var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("release").__call__(var2);
      var1.setline(393);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject release$56(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("acquire").__call__(var2);
      var1.setline(397);
      PyObject var3 = var1.getlocal(0).__getattr__("_Semaphore__value")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("_Semaphore__value", var3);
      var3 = null;
      var1.setline(398);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(399);
         var1.getlocal(0).__getattr__("_note").__call__((ThreadState)var2, PyString.fromInterned("%s.release: success, value=%s"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(0).__getattr__("_Semaphore__value"));
      }

      var1.setline(401);
      var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("notify").__call__(var2);
      var1.setline(402);
      var1.getlocal(0).__getattr__("_Semaphore__cond").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BoundedSemaphore$57(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyObject var10000 = var1.getglobal("_BoundedSemaphore");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _BoundedSemaphore$58(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Semaphore that checks that # releases is <= # acquires"));
      var1.setline(409);
      PyString.fromInterned("Semaphore that checks that # releases is <= # acquires");
      var1.setline(410);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$59, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(414);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$60, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, release$61, (PyObject)null);
      var1.setlocal("release", var4);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$62, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$59(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      var1.getglobal("_Semaphore").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(412);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_initial_value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$60(PyFrame var1, ThreadState var2) {
      var1.setline(415);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      var1.setline(416);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject release$61(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyObject var3 = var1.getlocal(0).__getattr__("_Semaphore__value");
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("_initial_value"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(420);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Semaphore released too many times"));
      } else {
         var1.setline(421);
         var3 = var1.getglobal("_Semaphore").__getattr__("release").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __exit__$62(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Event$63(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyObject var10000 = var1.getglobal("_Event");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _Event$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(434);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$65, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(439);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isSet$66, (PyObject)null);
      var1.setlocal("isSet", var4);
      var3 = null;
      var1.setline(442);
      PyObject var5 = var1.getname("isSet");
      var1.setlocal("is_set", var5);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set$67, (PyObject)null);
      var1.setlocal("set", var4);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$68, (PyObject)null);
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(459);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, wait$69, (PyObject)null);
      var1.setlocal("wait", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$65(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      var1.getglobal("_Verbose").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(436);
      PyObject var3 = var1.getglobal("Condition").__call__(var2, var1.getglobal("Lock").__call__(var2));
      var1.getlocal(0).__setattr__("_Event__cond", var3);
      var3 = null;
      var1.setline(437);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_Event__flag", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isSet$66(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyObject var3 = var1.getlocal(0).__getattr__("_Event__flag");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set$67(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(447);
         PyObject var4 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_Event__flag", var4);
         var4 = null;
         var1.setline(448);
         var1.getlocal(0).__getattr__("_Event__cond").__getattr__("notifyAll").__call__(var2);
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(450);
         var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(450);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear$68(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(455);
         PyObject var4 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_Event__flag", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(457);
         var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(457);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject wait$69(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("acquire").__call__(var2);
      Throwable var3 = null;

      Throwable var10000;
      label31: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(462);
            if (var1.getlocal(0).__getattr__("_Event__flag").__not__().__nonzero__()) {
               var1.setline(463);
               var1.getlocal(0).__getattr__("_Event__cond").__getattr__("wait").__call__(var2, var1.getlocal(1));
            }

            var1.setline(466);
            var4 = var1.getlocal(0).__getattr__("_Event__flag");
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label31;
         }

         var1.setline(468);
         var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      var3 = var10000;
      Py.addTraceback(var3, var1);
      var1.setline(468);
      var1.getlocal(0).__getattr__("_Event__cond").__getattr__("release").__call__(var2);
      throw (Throwable)var3;
   }

   public threading$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Verbose$1 = Py.newCode(0, var2, var1, "_Verbose", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "args"};
      _note$3 = Py.newCode(3, var2, var1, "_note", 33, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Verbose$4 = Py.newCode(0, var2, var1, "_Verbose", 42, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 43, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      _note$6 = Py.newCode(2, var2, var1, "_note", 45, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func"};
      setprofile$7 = Py.newCode(1, var2, var1, "setprofile", 53, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func"};
      settrace$8 = Py.newCode(1, var2, var1, "settrace", 57, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Semaphore$9 = Py.newCode(0, var2, var1, "Semaphore", 64, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 65, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blocking"};
      acquire$11 = Py.newCode(2, var2, var1, "acquire", 70, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$12 = Py.newCode(1, var2, var1, "__enter__", 77, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      release$13 = Py.newCode(1, var2, var1, "release", 81, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t", "v", "tb"};
      __exit__$14 = Py.newCode(4, var2, var1, "__exit__", 84, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JavaThread$15 = Py.newCode(0, var2, var1, "JavaThread", 97, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "thread"};
      __init__$16 = Py.newCode(2, var2, var1, "__init__", 98, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_thread", "status"};
      __repr__$17 = Py.newCode(1, var2, var1, "__repr__", 102, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$18 = Py.newCode(2, var2, var1, "__eq__", 108, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$19 = Py.newCode(2, var2, var1, "__ne__", 114, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      start$20 = Py.newCode(1, var2, var1, "start", 117, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$21 = Py.newCode(1, var2, var1, "run", 123, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout", "millis", "millis_int", "nanos"};
      join$22 = Py.newCode(2, var2, var1, "join", 126, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ident$23 = Py.newCode(1, var2, var1, "ident", 139, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getName$24 = Py.newCode(1, var2, var1, "getName", 144, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      setName$25 = Py.newCode(2, var2, var1, "setName", 147, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isAlive$26 = Py.newCode(1, var2, var1, "isAlive", 152, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isDaemon$27 = Py.newCode(1, var2, var1, "isDaemon", 157, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "daemonic"};
      setDaemon$28 = Py.newCode(2, var2, var1, "setDaemon", 160, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      __tojava__$29 = Py.newCode(2, var2, var1, "__tojava__", 174, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"jthread", "pythread"};
      _register_thread$30 = Py.newCode(2, var2, var1, "_register_thread", 185, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"jthread"};
      _unregister_thread$31 = Py.newCode(1, var2, var1, "_unregister_thread", 188, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Thread$32 = Py.newCode(0, var2, var1, "Thread", 193, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "group", "target", "name", "args", "kwargs", "_thread"};
      __init__$33 = Py.newCode(6, var2, var1, "__init__", 194, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_thread$34 = Py.newCode(1, var2, var1, "_create_thread", 208, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$35 = Py.newCode(1, var2, var1, "run", 211, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc_type", "exc_value", "exc_tb"};
      _Thread__bootstrap$36 = Py.newCode(1, var2, var1, "_Thread__bootstrap", 215, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _Thread__stop$37 = Py.newCode(1, var2, var1, "_Thread__stop", 268, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _Thread__delete$38 = Py.newCode(1, var2, var1, "_Thread__delete", 271, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _MainThread$39 = Py.newCode(0, var2, var1, "_MainThread", 275, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kw", "atexit"};
      __init__$40 = Py.newCode(1, var2, var1, "__init__", 276, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_thread$41 = Py.newCode(1, var2, var1, "_create_thread", 287, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _set_daemon$42 = Py.newCode(1, var2, var1, "_set_daemon", 290, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t"};
      _MainThread__exitfunc$43 = Py.newCode(1, var2, var1, "_MainThread__exitfunc", 293, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      _pickSomeNonDaemonThread$44 = Py.newCode(0, var2, var1, "_pickSomeNonDaemonThread", 300, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"jthread", "pythread"};
      currentThread$45 = Py.newCode(0, var2, var1, "currentThread", 306, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      activeCount$46 = Py.newCode(0, var2, var1, "activeCount", 315, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      enumerate$47 = Py.newCode(0, var2, var1, "enumerate", 320, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      Timer$48 = Py.newCode(2, var2, var1, "Timer", 334, true, true, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Timer$49 = Py.newCode(0, var2, var1, "_Timer", 337, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "interval", "function", "args", "kwargs"};
      __init__$50 = Py.newCode(5, var2, var1, "__init__", 345, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      cancel$51 = Py.newCode(1, var2, var1, "cancel", 353, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$52 = Py.newCode(1, var2, var1, "run", 357, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Semaphore$53 = Py.newCode(0, var2, var1, "_Semaphore", 365, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "verbose"};
      __init__$54 = Py.newCode(3, var2, var1, "__init__", 369, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blocking", "rc"};
      acquire$55 = Py.newCode(2, var2, var1, "acquire", 376, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      release$56 = Py.newCode(1, var2, var1, "release", 395, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      BoundedSemaphore$57 = Py.newCode(2, var2, var1, "BoundedSemaphore", 405, true, true, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BoundedSemaphore$58 = Py.newCode(0, var2, var1, "_BoundedSemaphore", 408, false, false, self, 58, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "verbose"};
      __init__$59 = Py.newCode(3, var2, var1, "__init__", 410, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$60 = Py.newCode(1, var2, var1, "__enter__", 414, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      release$61 = Py.newCode(1, var2, var1, "release", 418, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t", "v", "tb"};
      __exit__$62 = Py.newCode(4, var2, var1, "__exit__", 423, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      Event$63 = Py.newCode(2, var2, var1, "Event", 427, true, true, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Event$64 = Py.newCode(0, var2, var1, "_Event", 430, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$65 = Py.newCode(2, var2, var1, "__init__", 434, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isSet$66 = Py.newCode(1, var2, var1, "isSet", 439, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      set$67 = Py.newCode(1, var2, var1, "set", 444, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$68 = Py.newCode(1, var2, var1, "clear", 452, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout"};
      wait$69 = Py.newCode(2, var2, var1, "wait", 459, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new threading$py("threading$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(threading$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._Verbose$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._note$3(var2, var3);
         case 4:
            return this._Verbose$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this._note$6(var2, var3);
         case 7:
            return this.setprofile$7(var2, var3);
         case 8:
            return this.settrace$8(var2, var3);
         case 9:
            return this.Semaphore$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.acquire$11(var2, var3);
         case 12:
            return this.__enter__$12(var2, var3);
         case 13:
            return this.release$13(var2, var3);
         case 14:
            return this.__exit__$14(var2, var3);
         case 15:
            return this.JavaThread$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.__repr__$17(var2, var3);
         case 18:
            return this.__eq__$18(var2, var3);
         case 19:
            return this.__ne__$19(var2, var3);
         case 20:
            return this.start$20(var2, var3);
         case 21:
            return this.run$21(var2, var3);
         case 22:
            return this.join$22(var2, var3);
         case 23:
            return this.ident$23(var2, var3);
         case 24:
            return this.getName$24(var2, var3);
         case 25:
            return this.setName$25(var2, var3);
         case 26:
            return this.isAlive$26(var2, var3);
         case 27:
            return this.isDaemon$27(var2, var3);
         case 28:
            return this.setDaemon$28(var2, var3);
         case 29:
            return this.__tojava__$29(var2, var3);
         case 30:
            return this._register_thread$30(var2, var3);
         case 31:
            return this._unregister_thread$31(var2, var3);
         case 32:
            return this.Thread$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this._create_thread$34(var2, var3);
         case 35:
            return this.run$35(var2, var3);
         case 36:
            return this._Thread__bootstrap$36(var2, var3);
         case 37:
            return this._Thread__stop$37(var2, var3);
         case 38:
            return this._Thread__delete$38(var2, var3);
         case 39:
            return this._MainThread$39(var2, var3);
         case 40:
            return this.__init__$40(var2, var3);
         case 41:
            return this._create_thread$41(var2, var3);
         case 42:
            return this._set_daemon$42(var2, var3);
         case 43:
            return this._MainThread__exitfunc$43(var2, var3);
         case 44:
            return this._pickSomeNonDaemonThread$44(var2, var3);
         case 45:
            return this.currentThread$45(var2, var3);
         case 46:
            return this.activeCount$46(var2, var3);
         case 47:
            return this.enumerate$47(var2, var3);
         case 48:
            return this.Timer$48(var2, var3);
         case 49:
            return this._Timer$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.cancel$51(var2, var3);
         case 52:
            return this.run$52(var2, var3);
         case 53:
            return this._Semaphore$53(var2, var3);
         case 54:
            return this.__init__$54(var2, var3);
         case 55:
            return this.acquire$55(var2, var3);
         case 56:
            return this.release$56(var2, var3);
         case 57:
            return this.BoundedSemaphore$57(var2, var3);
         case 58:
            return this._BoundedSemaphore$58(var2, var3);
         case 59:
            return this.__init__$59(var2, var3);
         case 60:
            return this.__enter__$60(var2, var3);
         case 61:
            return this.release$61(var2, var3);
         case 62:
            return this.__exit__$62(var2, var3);
         case 63:
            return this.Event$63(var2, var3);
         case 64:
            return this._Event$64(var2, var3);
         case 65:
            return this.__init__$65(var2, var3);
         case 66:
            return this.isSet$66(var2, var3);
         case 67:
            return this.set$67(var2, var3);
         case 68:
            return this.clear$68(var2, var3);
         case 69:
            return this.wait$69(var2, var3);
         default:
            return null;
      }
   }
}
