package org.python.modules;

import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyMethod;
import org.python.core.PyObject;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.__builtin__;

public class synchronize {
   public static Object _getSync(PyObject obj) {
      return Py.tojava(obj, Object.class);
   }

   public static PyObject apply_synchronized(PyObject syncObject, PyObject callable, PyObject args) {
      synchronized(_getSync(syncObject)) {
         return __builtin__.apply(callable, args);
      }
   }

   public static PyObject apply_synchronized(PyObject syncObject, PyObject callable, PyObject args, PyDictionary kws) {
      synchronized(_getSync(syncObject)) {
         return __builtin__.apply(callable, args, kws);
      }
   }

   public static PyObject make_synchronized(PyObject callable) {
      return new SynchronizedCallable(callable);
   }

   public static class SynchronizedCallable extends PyObject implements Traverseproc {
      PyObject callable;

      public SynchronizedCallable(PyObject callable) {
         this.callable = callable;
      }

      public PyObject __get__(PyObject obj, PyObject type) {
         return new PyMethod(this, obj, type);
      }

      public PyObject __call__() {
         throw Py.TypeError("synchronized callable called with 0 args");
      }

      public PyObject __call__(PyObject arg) {
         synchronized(synchronize._getSync(arg)) {
            return this.callable.__call__(arg);
         }
      }

      public PyObject __call__(PyObject arg1, PyObject arg2) {
         synchronized(synchronize._getSync(arg1)) {
            return this.callable.__call__(arg1, arg2);
         }
      }

      public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
         synchronized(synchronize._getSync(arg1)) {
            return this.callable.__call__(arg1, arg2, arg3);
         }
      }

      public PyObject __call__(PyObject[] args, String[] keywords) {
         if (args.length == 0) {
            throw Py.TypeError("synchronized callable called with 0 args");
         } else {
            synchronized(synchronize._getSync(args[0])) {
               return this.callable.__call__(args, keywords);
            }
         }
      }

      public PyObject __call__(PyObject arg1, PyObject[] args, String[] keywords) {
         synchronized(synchronize._getSync(arg1)) {
            return this.callable.__call__(arg1, args, keywords);
         }
      }

      public boolean isCallable() {
         return true;
      }

      public int traverse(Visitproc visit, Object arg) {
         return this.callable != null ? visit.visit(this.callable, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && ob == this.callable;
      }
   }
}
