import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("Queue.py")
public class Queue$py extends PyFunctionTable implements PyRunnable {
   static Queue$py self;
   static final PyCode f$0;
   static final PyCode Empty$1;
   static final PyCode Full$2;
   static final PyCode Queue$3;
   static final PyCode __init__$4;
   static final PyCode task_done$5;
   static final PyCode join$6;
   static final PyCode qsize$7;
   static final PyCode empty$8;
   static final PyCode full$9;
   static final PyCode put$10;
   static final PyCode put_nowait$11;
   static final PyCode get$12;
   static final PyCode get_nowait$13;
   static final PyCode _init$14;
   static final PyCode _qsize$15;
   static final PyCode _put$16;
   static final PyCode _get$17;
   static final PyCode PriorityQueue$18;
   static final PyCode _init$19;
   static final PyCode _qsize$20;
   static final PyCode _put$21;
   static final PyCode _get$22;
   static final PyCode LifoQueue$23;
   static final PyCode _init$24;
   static final PyCode _qsize$25;
   static final PyCode _put$26;
   static final PyCode _get$27;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A multi-producer, multi-consumer queue."));
      var1.setline(1);
      PyString.fromInterned("A multi-producer, multi-consumer queue.");
      var1.setline(3);
      String[] var3 = new String[]{"time"};
      PyObject[] var6 = imp.importFrom("time", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_time", var4);
      var4 = null;

      PyObject var8;
      try {
         var1.setline(5);
         var8 = imp.importOneAs("threading", var1, -1);
         var1.setlocal("_threading", var8);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(7);
         var4 = imp.importOneAs("dummy_threading", var1, -1);
         var1.setlocal("_threading", var4);
         var4 = null;
      }

      var1.setline(8);
      var3 = new String[]{"deque"};
      var6 = imp.importFrom("collections", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal("deque", var4);
      var4 = null;
      var1.setline(9);
      var8 = imp.importOne("heapq", var1, -1);
      var1.setlocal("heapq", var8);
      var3 = null;
      var1.setline(11);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("Empty"), PyString.fromInterned("Full"), PyString.fromInterned("Queue"), PyString.fromInterned("PriorityQueue"), PyString.fromInterned("LifoQueue")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(13);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Empty", var6, Empty$1);
      var1.setlocal("Empty", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(17);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Full", var6, Full$2);
      var1.setlocal("Full", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(21);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Queue", var6, Queue$3);
      var1.setlocal("Queue", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(212);
      var6 = new PyObject[]{var1.getname("Queue")};
      var4 = Py.makeClass("PriorityQueue", var6, PriorityQueue$18);
      var1.setlocal("PriorityQueue", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(231);
      var6 = new PyObject[]{var1.getname("Queue")};
      var4 = Py.makeClass("LifoQueue", var6, LifoQueue$23);
      var1.setlocal("LifoQueue", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Empty$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised by Queue.get(block=0)/get_nowait()."));
      var1.setline(14);
      PyString.fromInterned("Exception raised by Queue.get(block=0)/get_nowait().");
      var1.setline(15);
      return var1.getf_locals();
   }

   public PyObject Full$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised by Queue.put(block=0)/put_nowait()."));
      var1.setline(18);
      PyString.fromInterned("Exception raised by Queue.put(block=0)/put_nowait().");
      var1.setline(19);
      return var1.getf_locals();
   }

   public PyObject Queue$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Create a queue object with a given maximum size.\n\n    If maxsize is <= 0, the queue size is infinite.\n    "));
      var1.setline(25);
      PyString.fromInterned("Create a queue object with a given maximum size.\n\n    If maxsize is <= 0, the queue size is infinite.\n    ");
      var1.setline(26);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, task_done$5, PyString.fromInterned("Indicate that a formerly enqueued task is complete.\n\n        Used by Queue consumer threads.  For each get() used to fetch a task,\n        a subsequent call to task_done() tells the queue that the processing\n        on the task is complete.\n\n        If a join() is currently blocking, it will resume when all items\n        have been processed (meaning that a task_done() call was received\n        for every item that had been put() into the queue).\n\n        Raises a ValueError if called more times than there were items\n        placed in the queue.\n        "));
      var1.setlocal("task_done", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, join$6, PyString.fromInterned("Blocks until all items in the Queue have been gotten and processed.\n\n        The count of unfinished tasks goes up whenever an item is added to the\n        queue. The count goes down whenever a consumer thread calls task_done()\n        to indicate the item was retrieved and all work on it is complete.\n\n        When the count of unfinished tasks drops to zero, join() unblocks.\n        "));
      var1.setlocal("join", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, qsize$7, PyString.fromInterned("Return the approximate size of the queue (not reliable!)."));
      var1.setlocal("qsize", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, empty$8, PyString.fromInterned("Return True if the queue is empty, False otherwise (not reliable!)."));
      var1.setlocal("empty", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, full$9, PyString.fromInterned("Return True if the queue is full, False otherwise (not reliable!)."));
      var1.setlocal("full", var4);
      var3 = null;
      var1.setline(107);
      var3 = new PyObject[]{var1.getname("True"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, put$10, PyString.fromInterned("Put an item into the queue.\n\n        If optional args 'block' is true and 'timeout' is None (the default),\n        block if necessary until a free slot is available. If 'timeout' is\n        a positive number, it blocks at most 'timeout' seconds and raises\n        the Full exception if no free slot was available within that time.\n        Otherwise ('block' is false), put an item on the queue if a free slot\n        is immediately available, else raise the Full exception ('timeout'\n        is ignored in that case).\n        "));
      var1.setlocal("put", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, put_nowait$11, PyString.fromInterned("Put an item into the queue without blocking.\n\n        Only enqueue the item if a free slot is immediately available.\n        Otherwise raise the Full exception.\n        "));
      var1.setlocal("put_nowait", var4);
      var3 = null;
      var1.setline(150);
      var3 = new PyObject[]{var1.getname("True"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$12, PyString.fromInterned("Remove and return an item from the queue.\n\n        If optional args 'block' is true and 'timeout' is None (the default),\n        block if necessary until an item is available. If 'timeout' is\n        a positive number, it blocks at most 'timeout' seconds and raises\n        the Empty exception if no item was available within that time.\n        Otherwise ('block' is false), return an item if one is immediately\n        available, else raise the Empty exception ('timeout' is ignored\n        in that case).\n        "));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_nowait$13, PyString.fromInterned("Remove and return an item from the queue without blocking.\n\n        Only get an item if one is immediately available. Otherwise\n        raise the Empty exception.\n        "));
      var1.setlocal("get_nowait", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _init$14, (PyObject)null);
      var1.setlocal("_init", var4);
      var3 = null;
      var1.setline(200);
      var3 = new PyObject[]{var1.getname("len")};
      var4 = new PyFunction(var1.f_globals, var3, _qsize$15, (PyObject)null);
      var1.setlocal("_qsize", var4);
      var3 = null;
      var1.setline(204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _put$16, (PyObject)null);
      var1.setlocal("_put", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get$17, (PyObject)null);
      var1.setlocal("_get", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("maxsize", var3);
      var3 = null;
      var1.setline(28);
      var1.getlocal(0).__getattr__("_init").__call__(var2, var1.getlocal(1));
      var1.setline(33);
      var3 = var1.getglobal("_threading").__getattr__("Lock").__call__(var2);
      var1.getlocal(0).__setattr__("mutex", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("_threading").__getattr__("Condition").__call__(var2, var1.getlocal(0).__getattr__("mutex"));
      var1.getlocal(0).__setattr__("not_empty", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("_threading").__getattr__("Condition").__call__(var2, var1.getlocal(0).__getattr__("mutex"));
      var1.getlocal(0).__setattr__("not_full", var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getglobal("_threading").__getattr__("Condition").__call__(var2, var1.getlocal(0).__getattr__("mutex"));
      var1.getlocal(0).__setattr__("all_tasks_done", var3);
      var3 = null;
      var1.setline(43);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"unfinished_tasks", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject task_done$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Indicate that a formerly enqueued task is complete.\n\n        Used by Queue consumer threads.  For each get() used to fetch a task,\n        a subsequent call to task_done() tells the queue that the processing\n        on the task is complete.\n\n        If a join() is currently blocking, it will resume when all items\n        have been processed (meaning that a task_done() call was received\n        for every item that had been put() into the queue).\n\n        Raises a ValueError if called more times than there were items\n        placed in the queue.\n        ");
      var1.setline(59);
      var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(61);
         PyObject var4 = var1.getlocal(0).__getattr__("unfinished_tasks")._sub(Py.newInteger(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(62);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._le(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(63);
            var4 = var1.getlocal(1);
            var10000 = var4._lt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(64);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("task_done() called too many times")));
            }

            var1.setline(65);
            var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("notify_all").__call__(var2);
         }

         var1.setline(66);
         var4 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("unfinished_tasks", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(68);
         var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(68);
      var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject join$6(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("Blocks until all items in the Queue have been gotten and processed.\n\n        The count of unfinished tasks goes up whenever an item is added to the\n        queue. The count goes down whenever a consumer thread calls task_done()\n        to indicate the item was retrieved and all work on it is complete.\n\n        When the count of unfinished tasks drops to zero, join() unblocks.\n        ");
      var1.setline(79);
      var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         while(true) {
            var1.setline(81);
            if (!var1.getlocal(0).__getattr__("unfinished_tasks").__nonzero__()) {
               break;
            }

            var1.setline(82);
            var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("wait").__call__(var2);
         }
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(84);
         var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("release").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(84);
      var1.getlocal(0).__getattr__("all_tasks_done").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject qsize$7(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Return the approximate size of the queue (not reliable!).");
      var1.setline(88);
      var1.getlocal(0).__getattr__("mutex").__getattr__("acquire").__call__(var2);
      var1.setline(89);
      PyObject var3 = var1.getlocal(0).__getattr__("_qsize").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      var1.getlocal(0).__getattr__("mutex").__getattr__("release").__call__(var2);
      var1.setline(91);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject empty$8(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Return True if the queue is empty, False otherwise (not reliable!).");
      var1.setline(95);
      var1.getlocal(0).__getattr__("mutex").__getattr__("acquire").__call__(var2);
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("_qsize").__call__(var2).__not__();
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(97);
      var1.getlocal(0).__getattr__("mutex").__getattr__("release").__call__(var2);
      var1.setline(98);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject full$9(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Return True if the queue is full, False otherwise (not reliable!).");
      var1.setline(102);
      var1.getlocal(0).__getattr__("mutex").__getattr__("acquire").__call__(var2);
      var1.setline(103);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(0).__getattr__("maxsize");
      PyInteger var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._lt(var10001)).__nonzero__()) {
         var4 = var5._eq(var1.getlocal(0).__getattr__("_qsize").__call__(var2));
      }

      var3 = null;
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(104);
      var1.getlocal(0).__getattr__("mutex").__getattr__("release").__call__(var2);
      var1.setline(105);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject put$10(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Put an item into the queue.\n\n        If optional args 'block' is true and 'timeout' is None (the default),\n        block if necessary until a free slot is available. If 'timeout' is\n        a positive number, it blocks at most 'timeout' seconds and raises\n        the Full exception if no free slot was available within that time.\n        Otherwise ('block' is false), put an item on the queue if a free slot\n        is immediately available, else raise the Full exception ('timeout'\n        is ignored in that case).\n        ");
      var1.setline(118);
      var1.getlocal(0).__getattr__("not_full").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(120);
         PyObject var4 = var1.getlocal(0).__getattr__("maxsize");
         PyObject var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(121);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(122);
               var4 = var1.getlocal(0).__getattr__("_qsize").__call__(var2);
               var10000 = var4._eq(var1.getlocal(0).__getattr__("maxsize"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(123);
                  throw Py.makeException(var1.getglobal("Full"));
               }
            } else {
               var1.setline(124);
               var4 = var1.getlocal(3);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(125);
                     var4 = var1.getlocal(0).__getattr__("_qsize").__call__(var2);
                     var10000 = var4._eq(var1.getlocal(0).__getattr__("maxsize"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(126);
                     var1.getlocal(0).__getattr__("not_full").__getattr__("wait").__call__(var2);
                  }
               } else {
                  var1.setline(127);
                  var4 = var1.getlocal(3);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(128);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'timeout' must be a positive number")));
                  }

                  var1.setline(130);
                  var4 = var1.getglobal("_time").__call__(var2)._add(var1.getlocal(3));
                  var1.setlocal(4, var4);
                  var4 = null;

                  while(true) {
                     var1.setline(131);
                     var4 = var1.getlocal(0).__getattr__("_qsize").__call__(var2);
                     var10000 = var4._eq(var1.getlocal(0).__getattr__("maxsize"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(132);
                     var4 = var1.getlocal(4)._sub(var1.getglobal("_time").__call__(var2));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(133);
                     var4 = var1.getlocal(5);
                     var10000 = var4._le(Py.newFloat(0.0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(134);
                        throw Py.makeException(var1.getglobal("Full"));
                     }

                     var1.setline(135);
                     var1.getlocal(0).__getattr__("not_full").__getattr__("wait").__call__(var2, var1.getlocal(5));
                  }
               }
            }
         }

         var1.setline(136);
         var1.getlocal(0).__getattr__("_put").__call__(var2, var1.getlocal(1));
         var1.setline(137);
         var10000 = var1.getlocal(0);
         String var8 = "unfinished_tasks";
         PyObject var5 = var10000;
         PyObject var6 = var5.__getattr__(var8);
         var6 = var6._iadd(Py.newInteger(1));
         var5.__setattr__(var8, var6);
         var1.setline(138);
         var1.getlocal(0).__getattr__("not_empty").__getattr__("notify").__call__(var2);
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(140);
         var1.getlocal(0).__getattr__("not_full").__getattr__("release").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(140);
      var1.getlocal(0).__getattr__("not_full").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject put_nowait$11(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyString.fromInterned("Put an item into the queue without blocking.\n\n        Only enqueue the item if a free slot is immediately available.\n        Otherwise raise the Full exception.\n        ");
      var1.setline(148);
      PyObject var3 = var1.getlocal(0).__getattr__("put").__call__(var2, var1.getlocal(1), var1.getglobal("False"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$12(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned("Remove and return an item from the queue.\n\n        If optional args 'block' is true and 'timeout' is None (the default),\n        block if necessary until an item is available. If 'timeout' is\n        a positive number, it blocks at most 'timeout' seconds and raises\n        the Empty exception if no item was available within that time.\n        Otherwise ('block' is false), return an item if one is immediately\n        available, else raise the Empty exception ('timeout' is ignored\n        in that case).\n        ");
      var1.setline(161);
      var1.getlocal(0).__getattr__("not_empty").__getattr__("acquire").__call__(var2);
      Throwable var3 = null;

      Throwable var10000;
      label59: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(163);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(164);
               if (var1.getlocal(0).__getattr__("_qsize").__call__(var2).__not__().__nonzero__()) {
                  var1.setline(165);
                  throw Py.makeException(var1.getglobal("Empty"));
               }
            } else {
               var1.setline(166);
               var4 = var1.getlocal(2);
               PyObject var7 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var7.__nonzero__()) {
                  while(true) {
                     var1.setline(167);
                     if (!var1.getlocal(0).__getattr__("_qsize").__call__(var2).__not__().__nonzero__()) {
                        break;
                     }

                     var1.setline(168);
                     var1.getlocal(0).__getattr__("not_empty").__getattr__("wait").__call__(var2);
                  }
               } else {
                  var1.setline(169);
                  var4 = var1.getlocal(2);
                  var7 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var7.__nonzero__()) {
                     var1.setline(170);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'timeout' must be a positive number")));
                  }

                  var1.setline(172);
                  var4 = var1.getglobal("_time").__call__(var2)._add(var1.getlocal(2));
                  var1.setlocal(3, var4);
                  var4 = null;

                  while(true) {
                     var1.setline(173);
                     if (!var1.getlocal(0).__getattr__("_qsize").__call__(var2).__not__().__nonzero__()) {
                        break;
                     }

                     var1.setline(174);
                     var4 = var1.getlocal(3)._sub(var1.getglobal("_time").__call__(var2));
                     var1.setlocal(4, var4);
                     var4 = null;
                     var1.setline(175);
                     var4 = var1.getlocal(4);
                     var7 = var4._le(Py.newFloat(0.0));
                     var4 = null;
                     if (var7.__nonzero__()) {
                        var1.setline(176);
                        throw Py.makeException(var1.getglobal("Empty"));
                     }

                     var1.setline(177);
                     var1.getlocal(0).__getattr__("not_empty").__getattr__("wait").__call__(var2, var1.getlocal(4));
                  }
               }
            }

            var1.setline(178);
            var4 = var1.getlocal(0).__getattr__("_get").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(179);
            var1.getlocal(0).__getattr__("not_full").__getattr__("notify").__call__(var2);
            var1.setline(180);
            var4 = var1.getlocal(5);
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label59;
         }

         var1.setline(182);
         var1.getlocal(0).__getattr__("not_empty").__getattr__("release").__call__(var2);

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
      var1.setline(182);
      var1.getlocal(0).__getattr__("not_empty").__getattr__("release").__call__(var2);
      throw (Throwable)var3;
   }

   public PyObject get_nowait$13(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Remove and return an item from the queue without blocking.\n\n        Only get an item if one is immediately available. Otherwise\n        raise the Empty exception.\n        ");
      var1.setline(190);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getglobal("False"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _init$14(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getglobal("deque").__call__(var2);
      var1.getlocal(0).__setattr__("queue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _qsize$15(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("queue"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _put$16(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      var1.getlocal(0).__getattr__("queue").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get$17(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("queue").__getattr__("popleft").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject PriorityQueue$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Variant of Queue that retrieves open entries in priority order (lowest first).\n\n    Entries are typically tuples of the form:  (priority number, data).\n    "));
      var1.setline(216);
      PyString.fromInterned("Variant of Queue that retrieves open entries in priority order (lowest first).\n\n    Entries are typically tuples of the form:  (priority number, data).\n    ");
      var1.setline(218);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _init$19, (PyObject)null);
      var1.setlocal("_init", var4);
      var3 = null;
      var1.setline(221);
      var3 = new PyObject[]{var1.getname("len")};
      var4 = new PyFunction(var1.f_globals, var3, _qsize$20, (PyObject)null);
      var1.setlocal("_qsize", var4);
      var3 = null;
      var1.setline(224);
      var3 = new PyObject[]{var1.getname("heapq").__getattr__("heappush")};
      var4 = new PyFunction(var1.f_globals, var3, _put$21, (PyObject)null);
      var1.setlocal("_put", var4);
      var3 = null;
      var1.setline(227);
      var3 = new PyObject[]{var1.getname("heapq").__getattr__("heappop")};
      var4 = new PyFunction(var1.f_globals, var3, _get$22, (PyObject)null);
      var1.setlocal("_get", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _init$19(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"queue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _qsize$20(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("queue"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _put$21(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("queue"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get$22(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("queue"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LifoQueue$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Variant of Queue that retrieves most recently added entries first."));
      var1.setline(232);
      PyString.fromInterned("Variant of Queue that retrieves most recently added entries first.");
      var1.setline(234);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _init$24, (PyObject)null);
      var1.setlocal("_init", var4);
      var3 = null;
      var1.setline(237);
      var3 = new PyObject[]{var1.getname("len")};
      var4 = new PyFunction(var1.f_globals, var3, _qsize$25, (PyObject)null);
      var1.setlocal("_qsize", var4);
      var3 = null;
      var1.setline(240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _put$26, (PyObject)null);
      var1.setlocal("_put", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get$27, (PyObject)null);
      var1.setlocal("_get", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _init$24(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"queue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _qsize$25(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("queue"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _put$26(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      var1.getlocal(0).__getattr__("queue").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get$27(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getlocal(0).__getattr__("queue").__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public Queue$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Empty$1 = Py.newCode(0, var2, var1, "Empty", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Full$2 = Py.newCode(0, var2, var1, "Full", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Queue$3 = Py.newCode(0, var2, var1, "Queue", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "maxsize"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unfinished"};
      task_done$5 = Py.newCode(1, var2, var1, "task_done", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      join$6 = Py.newCode(1, var2, var1, "join", 70, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      qsize$7 = Py.newCode(1, var2, var1, "qsize", 86, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      empty$8 = Py.newCode(1, var2, var1, "empty", 93, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      full$9 = Py.newCode(1, var2, var1, "full", 100, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item", "block", "timeout", "endtime", "remaining"};
      put$10 = Py.newCode(4, var2, var1, "put", 107, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      put_nowait$11 = Py.newCode(2, var2, var1, "put_nowait", 142, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block", "timeout", "endtime", "remaining", "item"};
      get$12 = Py.newCode(3, var2, var1, "get", 150, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_nowait$13 = Py.newCode(1, var2, var1, "get_nowait", 184, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "maxsize"};
      _init$14 = Py.newCode(2, var2, var1, "_init", 197, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      _qsize$15 = Py.newCode(2, var2, var1, "_qsize", 200, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      _put$16 = Py.newCode(2, var2, var1, "_put", 204, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get$17 = Py.newCode(1, var2, var1, "_get", 208, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PriorityQueue$18 = Py.newCode(0, var2, var1, "PriorityQueue", 212, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "maxsize"};
      _init$19 = Py.newCode(2, var2, var1, "_init", 218, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      _qsize$20 = Py.newCode(2, var2, var1, "_qsize", 221, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item", "heappush"};
      _put$21 = Py.newCode(3, var2, var1, "_put", 224, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "heappop"};
      _get$22 = Py.newCode(2, var2, var1, "_get", 227, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LifoQueue$23 = Py.newCode(0, var2, var1, "LifoQueue", 231, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "maxsize"};
      _init$24 = Py.newCode(2, var2, var1, "_init", 234, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      _qsize$25 = Py.newCode(2, var2, var1, "_qsize", 237, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      _put$26 = Py.newCode(2, var2, var1, "_put", 240, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get$27 = Py.newCode(1, var2, var1, "_get", 243, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new Queue$py("Queue$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(Queue$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Empty$1(var2, var3);
         case 2:
            return this.Full$2(var2, var3);
         case 3:
            return this.Queue$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.task_done$5(var2, var3);
         case 6:
            return this.join$6(var2, var3);
         case 7:
            return this.qsize$7(var2, var3);
         case 8:
            return this.empty$8(var2, var3);
         case 9:
            return this.full$9(var2, var3);
         case 10:
            return this.put$10(var2, var3);
         case 11:
            return this.put_nowait$11(var2, var3);
         case 12:
            return this.get$12(var2, var3);
         case 13:
            return this.get_nowait$13(var2, var3);
         case 14:
            return this._init$14(var2, var3);
         case 15:
            return this._qsize$15(var2, var3);
         case 16:
            return this._put$16(var2, var3);
         case 17:
            return this._get$17(var2, var3);
         case 18:
            return this.PriorityQueue$18(var2, var3);
         case 19:
            return this._init$19(var2, var3);
         case 20:
            return this._qsize$20(var2, var3);
         case 21:
            return this._put$21(var2, var3);
         case 22:
            return this._get$22(var2, var3);
         case 23:
            return this.LifoQueue$23(var2, var3);
         case 24:
            return this._init$24(var2, var3);
         case 25:
            return this._qsize$25(var2, var3);
         case 26:
            return this._put$26(var2, var3);
         case 27:
            return this._get$27(var2, var3);
         default:
            return null;
      }
   }
}
