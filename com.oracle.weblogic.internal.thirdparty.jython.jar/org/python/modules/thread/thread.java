package org.python.modules.thread;

import org.python.core.ClassDictInit;
import org.python.core.FunctionThread;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;

public class thread implements ClassDictInit {
   private static volatile long stack_size = 0L;
   private static ThreadGroup group = new ThreadGroup("jython-threads");
   public static PyString __doc__ = new PyString("This module provides primitive operations to write multi-threaded programs.\nThe 'threading' module provides a more convenient interface.");
   public static PyObject error = new PyString("thread.error");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"LockType", PyType.fromClass(PyLock.class));
      dict.__setitem__((String)"_local", PyLocal.TYPE);
      dict.__setitem__((String)"interruptAllThreads", (PyObject)null);
   }

   public static void start_new_thread(PyObject func, PyTuple args) {
      Thread pt = _newFunctionThread(func, args);
      PyObject currentThread = func.__findattr__("__self__");
      if (currentThread != null) {
         PyObject isDaemon = currentThread.__findattr__("isDaemon");
         PyObject getName;
         if (isDaemon != null && isDaemon.isCallable()) {
            getName = isDaemon.__call__();
            pt.setDaemon(getName.__nonzero__());
         }

         getName = currentThread.__findattr__("getName");
         if (getName != null && getName.isCallable()) {
            PyObject pname = getName.__call__();
            pt.setName(String.valueOf(pname));
         }
      }

      pt.start();
   }

   public static FunctionThread _newFunctionThread(PyObject func, PyTuple args) {
      return new FunctionThread(func, args.getArray(), stack_size, group);
   }

   public static void interruptAllThreads() {
      group.interrupt();
   }

   public static PyLock allocate_lock() {
      return new PyLock();
   }

   public static void exit() {
      exit_thread();
   }

   public static void exit_thread() {
      throw new PyException(Py.SystemExit, new PyInteger(0));
   }

   public static long get_ident() {
      return Thread.currentThread().getId();
   }

   public static long stack_size(PyObject[] args) {
      switch (args.length) {
         case 0:
            return stack_size;
         case 1:
            long old_stack_size = stack_size;
            int proposed_stack_size = args[0].asInt();
            if (proposed_stack_size != 0 && proposed_stack_size < 32768) {
               throw Py.ValueError("size not valid: " + proposed_stack_size + " bytes");
            }

            stack_size = (long)proposed_stack_size;
            return old_stack_size;
         default:
            throw Py.TypeError("stack_size() takes at most 1 argument (" + args.length + "given)");
      }
   }
}
