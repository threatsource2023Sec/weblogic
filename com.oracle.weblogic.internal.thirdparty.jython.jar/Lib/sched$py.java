import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("sched.py")
public class sched$py extends PyFunctionTable implements PyRunnable {
   static sched$py self;
   static final PyCode f$0;
   static final PyCode scheduler$1;
   static final PyCode __init__$2;
   static final PyCode enterabs$3;
   static final PyCode enter$4;
   static final PyCode cancel$5;
   static final PyCode empty$6;
   static final PyCode run$7;
   static final PyCode queue$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A generally useful event scheduler class.\n\nEach instance of this class manages its own queue.\nNo multi-threading is implied; you are supposed to hack that\nyourself, or use a single instance per application.\n\nEach instance is parametrized with two functions, one that is\nsupposed to return the current time, one that is supposed to\nimplement a delay.  You can implement real-time scheduling by\nsubstituting time and sleep from built-in module time, or you can\nimplement simulated time by writing your own functions.  This can\nalso be used to integrate scheduling with STDWIN events; the delay\nfunction is allowed to modify the queue.  Time can be expressed as\nintegers or floating point numbers, as long as it is consistent.\n\nEvents are specified by tuples (time, priority, action, argument).\nAs in UNIX, lower priority numbers mean higher priority; in this\nway the queue can be maintained as a priority queue.  Execution of the\nevent means calling the action function, passing it the argument\nsequence in \"argument\" (remember that in Python, multiple function\narguments are be packed in a sequence).\nThe action function may be an instance method so it\nhas another way to reference private data (besides global variables).\n"));
      var1.setline(24);
      PyString.fromInterned("A generally useful event scheduler class.\n\nEach instance of this class manages its own queue.\nNo multi-threading is implied; you are supposed to hack that\nyourself, or use a single instance per application.\n\nEach instance is parametrized with two functions, one that is\nsupposed to return the current time, one that is supposed to\nimplement a delay.  You can implement real-time scheduling by\nsubstituting time and sleep from built-in module time, or you can\nimplement simulated time by writing your own functions.  This can\nalso be used to integrate scheduling with STDWIN events; the delay\nfunction is allowed to modify the queue.  Time can be expressed as\nintegers or floating point numbers, as long as it is consistent.\n\nEvents are specified by tuples (time, priority, action, argument).\nAs in UNIX, lower priority numbers mean higher priority; in this\nway the queue can be maintained as a priority queue.  Execution of the\nevent means calling the action function, passing it the argument\nsequence in \"argument\" (remember that in Python, multiple function\narguments are be packed in a sequence).\nThe action function may be an instance method so it\nhas another way to reference private data (besides global variables).\n");
      var1.setline(31);
      PyObject var3 = imp.importOne("heapq", var1, -1);
      var1.setlocal("heapq", var3);
      var3 = null;
      var1.setline(32);
      String[] var5 = new String[]{"namedtuple"};
      PyObject[] var6 = imp.importFrom("collections", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var1.setline(34);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("scheduler")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(36);
      var3 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Event"), (PyObject)PyString.fromInterned("time, priority, action, argument"));
      var1.setlocal("Event", var3);
      var3 = null;
      var1.setline(38);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("scheduler", var6, scheduler$1);
      var1.setlocal("scheduler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject scheduler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(39);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Initialize a new instance, passing the time and delay\n        functions"));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, enterabs$3, PyString.fromInterned("Enter a new event in the queue at an absolute time.\n\n        Returns an ID for the event which can be used to remove it,\n        if necessary.\n\n        "));
      var1.setlocal("enterabs", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, enter$4, PyString.fromInterned("A variant that specifies the time as a relative time.\n\n        This is actually the more commonly used interface.\n\n        "));
      var1.setlocal("enter", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cancel$5, PyString.fromInterned("Remove an event from the queue.\n\n        This must be presented the ID as returned by enter().\n        If the event is not in the queue, this raises ValueError.\n\n        "));
      var1.setlocal("cancel", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, empty$6, PyString.fromInterned("Check whether the queue is empty."));
      var1.setlocal("empty", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$7, PyString.fromInterned("Execute events until the queue is empty.\n\n        When there is a positive delay until the first event, the\n        delay function is called and the event is left in the queue;\n        otherwise, the event is removed from the queue and executed\n        (its action function is called, passing it the argument).  If\n        the delay function returns prematurely, it is simply\n        restarted.\n\n        It is legal for both the delay function and the action\n        function to modify the queue or to raise an exception;\n        exceptions are not caught but the scheduler's state remains\n        well-defined so run() may be called again.\n\n        A questionable hack is added to allow other threads to run:\n        just after an event is executed, a delay of 0 is executed, to\n        avoid monopolizing the CPU when other threads are also\n        runnable.\n\n        "));
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, queue$8, PyString.fromInterned("An ordered list of upcoming events.\n\n        Events are named tuples with fields for:\n            time, priority, action, arguments\n\n        "));
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("queue", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyString.fromInterned("Initialize a new instance, passing the time and delay\n        functions");
      var1.setline(42);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_queue", var3);
      var3 = null;
      var1.setline(43);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("timefunc", var4);
      var3 = null;
      var1.setline(44);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("delayfunc", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject enterabs$3(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Enter a new event in the queue at an absolute time.\n\n        Returns an ID for the event which can be used to remove it,\n        if necessary.\n\n        ");
      var1.setline(53);
      PyObject var3 = var1.getglobal("Event").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(54);
      var1.getglobal("heapq").__getattr__("heappush").__call__(var2, var1.getlocal(0).__getattr__("_queue"), var1.getlocal(5));
      var1.setline(55);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject enter$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("A variant that specifies the time as a relative time.\n\n        This is actually the more commonly used interface.\n\n        ");
      var1.setline(63);
      PyObject var3 = var1.getlocal(0).__getattr__("timefunc").__call__(var2)._add(var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(0).__getattr__("enterabs").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cancel$5(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Remove an event from the queue.\n\n        This must be presented the ID as returned by enter().\n        If the event is not in the queue, this raises ValueError.\n\n        ");
      var1.setline(73);
      var1.getlocal(0).__getattr__("_queue").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.setline(74);
      var1.getglobal("heapq").__getattr__("heapify").__call__(var2, var1.getlocal(0).__getattr__("_queue"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject empty$6(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Check whether the queue is empty.");
      var1.setline(78);
      PyObject var3 = var1.getlocal(0).__getattr__("_queue").__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$7(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyString.fromInterned("Execute events until the queue is empty.\n\n        When there is a positive delay until the first event, the\n        delay function is called and the event is left in the queue;\n        otherwise, the event is removed from the queue and executed\n        (its action function is called, passing it the argument).  If\n        the delay function returns prematurely, it is simply\n        restarted.\n\n        It is legal for both the delay function and the action\n        function to modify the queue or to raise an exception;\n        exceptions are not caught but the scheduler's state remains\n        well-defined so run() may be called again.\n\n        A questionable hack is added to allow other threads to run:\n        just after an event is executed, a delay of 0 is executed, to\n        avoid monopolizing the CPU when other threads are also\n        runnable.\n\n        ");
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__getattr__("_queue");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getlocal(0).__getattr__("delayfunc");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(105);
      var3 = var1.getlocal(0).__getattr__("timefunc");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getglobal("heapq").__getattr__("heappop");
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(107);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(108);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject[] var4 = Py.unpackSequence(var3, 4);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(8, var5);
         var5 = null;
         var1.setlocal(9, var3);
         var1.setline(109);
         var3 = var1.getlocal(3).__call__(var2);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(110);
         var3 = var1.getlocal(10);
         PyObject var10000 = var3._lt(var1.getlocal(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(111);
            var1.getlocal(2).__call__(var2, var1.getlocal(5)._sub(var1.getlocal(10)));
         } else {
            var1.setline(113);
            var3 = var1.getlocal(4).__call__(var2, var1.getlocal(1));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(116);
            var3 = var1.getlocal(11);
            var10000 = var3._is(var1.getlocal(9));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(117);
               var10000 = var1.getlocal(7);
               PyObject[] var7 = Py.EmptyObjects;
               String[] var6 = new String[0];
               var10000._callextra(var7, var6, var1.getlocal(8), (PyObject)null);
               var3 = null;
               var1.setline(118);
               var1.getlocal(2).__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            } else {
               var1.setline(120);
               var1.getglobal("heapq").__getattr__("heappush").__call__(var2, var1.getlocal(1), var1.getlocal(11));
            }
         }
      }
   }

   public PyObject queue$8(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyString.fromInterned("An ordered list of upcoming events.\n\n        Events are named tuples with fields for:\n            time, priority, action, arguments\n\n        ");
      var1.setline(133);
      PyObject var3 = var1.getlocal(0).__getattr__("_queue").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getglobal("map").__call__(var2, var1.getglobal("heapq").__getattr__("heappop"), (new PyList(new PyObject[]{var1.getlocal(1)}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public sched$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      scheduler$1 = Py.newCode(0, var2, var1, "scheduler", 38, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "timefunc", "delayfunc"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 39, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "time", "priority", "action", "argument", "event"};
      enterabs$3 = Py.newCode(5, var2, var1, "enterabs", 46, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "delay", "priority", "action", "argument", "time"};
      enter$4 = Py.newCode(5, var2, var1, "enter", 57, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event"};
      cancel$5 = Py.newCode(2, var2, var1, "cancel", 66, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      empty$6 = Py.newCode(1, var2, var1, "empty", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "q", "delayfunc", "timefunc", "pop", "time", "priority", "action", "argument", "checked_event", "now", "event"};
      run$7 = Py.newCode(1, var2, var1, "run", 80, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "events"};
      queue$8 = Py.newCode(1, var2, var1, "queue", 122, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sched$py("sched$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sched$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.scheduler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.enterabs$3(var2, var3);
         case 4:
            return this.enter$4(var2, var3);
         case 5:
            return this.cancel$5(var2, var3);
         case 6:
            return this.empty$6(var2, var3);
         case 7:
            return this.run$7(var2, var3);
         case 8:
            return this.queue$8(var2, var3);
         default:
            return null;
      }
   }
}
