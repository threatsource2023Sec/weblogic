package org.python.modules.itertools;

import java.util.Iterator;
import java.util.Map;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyIterator;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@ExposedType(
   name = "itertools.tee",
   base = PyObject.class,
   isBaseType = false,
   doc = "Iterator wrapped to make it copyable"
)
public class PyTeeIterator extends PyIterator {
   public static final String tee_doc = "Iterator wrapped to make it copyable";
   private int position;
   private PyTeeData teeData;

   public PyTeeIterator() {
   }

   public PyTeeIterator(PyType subType) {
      super(subType);
   }

   public PyTeeIterator(PyTeeData teeData) {
      this.teeData = teeData;
   }

   @ExposedNew
   static final PyObject tee___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      int nargs = args.length;
      if (nargs >= 1 && nargs <= 1) {
         return fromIterable(args[0]);
      } else {
         throw Py.TypeError("tee expected 1 arguments, got " + nargs);
      }
   }

   public static PyObject[] makeTees(PyObject iterable, int n) {
      if (n < 0) {
         throw Py.ValueError("n must be >= 0");
      } else {
         PyObject[] tees = new PyObject[n];
         if (n == 0) {
            return tees;
         } else {
            PyObject copyFunc = iterable.__findattr__("__copy__");
            if (copyFunc == null) {
               tees[0] = fromIterable(iterable);
               copyFunc = tees[0].__getattr__("__copy__");
            } else {
               tees[0] = iterable;
            }

            for(int i = 1; i < n; ++i) {
               tees[i] = copyFunc.__call__();
            }

            return tees;
         }
      }
   }

   private static PyTeeIterator fromIterable(PyObject iterable) {
      if (iterable instanceof PyTeeIterator) {
         return ((PyTeeIterator)iterable).tee___copy__();
      } else {
         PyObject iterator = iterable.__iter__();
         PyTeeData teeData = new PyTeeData(iterator);
         return new PyTeeIterator(teeData);
      }
   }

   public final PyObject tee_next() {
      return this.next();
   }

   public PyObject __iternext__() {
      PyObject obj = this.teeData.getItem(this.position++);
      if (obj == null) {
         this.stopException = this.teeData.stopException;
      }

      return obj;
   }

   public final PyTeeIterator tee___copy__() {
      return new PyTeeIterator(this.teeData);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.teeData != null) {
            if (this.teeData.iterator != null) {
               retVal = visit.visit(this.teeData.iterator, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }

            if (this.teeData.buffer != null) {
               Iterator var4 = this.teeData.buffer.values().iterator();

               while(var4.hasNext()) {
                  PyObject ob = (PyObject)var4.next();
                  if (ob != null) {
                     retVal = visit.visit(ob, arg);
                     if (retVal != 0) {
                        return retVal;
                     }
                  }
               }
            }

            if (this.teeData.stopException != null) {
               retVal = this.teeData.stopException.traverse(visit, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }

         return 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else if (super.refersDirectlyTo(ob)) {
         return true;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   static {
      PyType.addBuilder(PyTeeIterator.class, new PyExposer());
   }

   private static class PyTeeData {
      private PyObject iterator;
      private int total;
      private Map buffer;
      public PyException stopException;
      private Object lock;

      public PyTeeData(PyObject iterator) {
         this.iterator = iterator;
         this.buffer = Generic.concurrentMap();
         this.total = 0;
         this.lock = new Object();
      }

      public PyObject getItem(int pos) {
         if (pos == this.total) {
            synchronized(this.lock) {
               if (pos == this.total) {
                  PyObject obj = this.nextElement(this.iterator);
                  if (obj == null) {
                     return null;
                  }

                  this.buffer.put(this.total++, obj);
               }
            }
         }

         return (PyObject)this.buffer.get(pos);
      }

      private PyObject nextElement(PyObject pyIter) {
         PyObject element = null;

         try {
            element = pyIter.__iternext__();
         } catch (PyException var4) {
            if (!var4.match(Py.StopIteration)) {
               throw var4;
            }

            this.stopException = var4;
         }

         return element;
      }
   }

   private static class tee_next_exposer extends PyBuiltinMethodNarrow {
      public tee_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public tee_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tee_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyTeeIterator)this.self).tee_next();
      }
   }

   private static class tee___copy___exposer extends PyBuiltinMethodNarrow {
      public tee___copy___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public tee___copy___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tee___copy___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyTeeIterator)this.self).tee___copy__();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyTeeIterator.tee___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new tee_next_exposer("next"), new tee___copy___exposer("__copy__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.tee", PyTeeIterator.class, PyObject.class, (boolean)0, "Iterator wrapped to make it copyable", var1, var2, new exposed___new__());
      }
   }
}
