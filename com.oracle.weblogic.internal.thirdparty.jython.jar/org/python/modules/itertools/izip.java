package org.python.modules.itertools;

import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyIterator;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyXRange;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.izip",
   base = PyObject.class,
   doc = "izip(iter1 [,iter2 [...]]) --> izip object\n\nReturn an izip object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the shortest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  Works like the zip()\nfunction but consumes less memory by returning an iterator instead of\na list."
)
public class izip extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String izip_doc = "izip(iter1 [,iter2 [...]]) --> izip object\n\nReturn an izip object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the shortest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  Works like the zip()\nfunction but consumes less memory by returning an iterator instead of\na list.";

   public izip() {
   }

   public izip(PyType subType) {
      super(subType);
   }

   public izip(PyObject... args) {
      this.izip___init__(args);
   }

   @ExposedNew
   final void izip___init__(PyObject[] args, String[] kwds) {
      if (kwds.length > 0) {
         throw Py.TypeError(String.format("izip does not take keyword arguments"));
      } else {
         this.izip___init__(args);
      }
   }

   private void izip___init__(PyObject[] argstar) {
      final int itemsize = argstar.length;
      if (itemsize == 0) {
         this.iter = (PyIterator)((PyIterator)(new PyXRange(0)).__iter__());
      } else {
         final PyObject[] iters = new PyObject[itemsize];

         for(int i = 0; i < itemsize; ++i) {
            PyObject iter = argstar[i].__iter__();
            if (iter == null) {
               throw Py.TypeError("izip argument #" + (i + 1) + " must support iteration");
            }

            iters[i] = iter;
         }

         this.iter = new itertools.ItertoolsIterator() {
            public PyObject __iternext__() {
               if (itemsize == 0) {
                  return null;
               } else {
                  PyObject[] next = new PyObject[itemsize];

                  for(int i = 0; i < itemsize; ++i) {
                     PyObject item = this.nextElement(iters[i]);
                     if (item == null) {
                        return null;
                     }

                     next[i] = item;
                  }

                  return new PyTuple(next);
               }
            }
         };
      }
   }

   public PyObject __iternext__() {
      return this.iter.__iternext__();
   }

   public PyObject next() {
      return this.doNext(this.__iternext__());
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         return this.iter != null ? visit.visit(this.iter, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.iter == ob || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(izip.class, new PyExposer());
      TYPE = PyType.fromClass(izip.class);
   }

   private static class izip___init___exposer extends PyBuiltinMethod {
      public izip___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public izip___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new izip___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((izip)this.self).izip___init__(var1, var2);
         return Py.None;
      }
   }

   private static class next_exposer extends PyBuiltinMethodNarrow {
      public next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((izip)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         izip var4 = new izip(this.for_type);
         if (var1) {
            var4.izip___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new izipDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new izip___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.izip", izip.class, PyObject.class, (boolean)1, "izip(iter1 [,iter2 [...]]) --> izip object\n\nReturn an izip object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the shortest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  Works like the zip()\nfunction but consumes less memory by returning an iterator instead of\na list.", var1, var2, new exposed___new__());
      }
   }
}
