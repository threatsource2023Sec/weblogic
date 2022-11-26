package org.python.core;

import java.util.concurrent.atomic.AtomicInteger;
import org.python.modules._systemrestart;

public class FunctionThread extends Thread {
   private final PyObject func;
   private final PyObject[] args;
   private final PySystemState systemState;
   private static AtomicInteger counter = new AtomicInteger();

   public FunctionThread(PyObject func, PyObject[] args, long stack_size, ThreadGroup group) {
      super(group, (Runnable)null, "Thread", stack_size);
      this.func = func;
      this.args = args;
      this.systemState = Py.getSystemState();
      this.setName("Thread-" + Integer.toString(counter.incrementAndGet()));
   }

   public void run() {
      Py.setSystemState(this.systemState);

      try {
         this.func.__call__(this.args);
      } catch (PyException var2) {
         if (var2.match(Py.SystemExit) || var2.match(_systemrestart.SystemRestart)) {
            return;
         }

         Py.stderr.println("Unhandled exception in thread started by " + this.func);
         Py.printException(var2);
      }

   }

   public String toString() {
      ThreadGroup group = this.getThreadGroup();
      return group != null ? String.format("FunctionThread[%s,%s,%s]", this.getName(), this.getPriority(), group.getName()) : String.format("FunctionThread[%s,%s]", this.getName(), this.getPriority());
   }
}
