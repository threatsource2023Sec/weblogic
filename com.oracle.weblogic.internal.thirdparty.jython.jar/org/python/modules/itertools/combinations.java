package org.python.modules.itertools;

import org.python.core.ArgParser;
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
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.combinations",
   base = PyObject.class,
   doc = "combinations(iterable, r) --> combinations object\n\nReturn successive r-length combinations of elements in the iterable.\n\ncombinations(range(4), 3) --> (0,1,2), (0,1,3), (0,2,3), (1,2,3)"
)
public class combinations extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String combinations_doc = "combinations(iterable, r) --> combinations object\n\nReturn successive r-length combinations of elements in the iterable.\n\ncombinations(range(4), 3) --> (0,1,2), (0,1,3), (0,2,3), (1,2,3)";

   public combinations() {
   }

   public combinations(PyType subType) {
      super(subType);
   }

   public combinations(PyObject iterable, int r) {
      this.combinations___init__(iterable, r);
   }

   @ExposedNew
   final void combinations___init__(PyObject[] args, String[] kwds) {
      if (args.length > 2) {
         throw Py.TypeError(String.format("combinations_with_replacement() takes at most 2 arguments (%d given)", args.length));
      } else {
         ArgParser ap = new ArgParser("combinations_with_replacement", args, kwds, "iterable", "r");
         PyObject iterable = ap.getPyObject(0);
         int r = ap.getInt(1);
         if (r < 0) {
            throw Py.ValueError("r must be non-negative");
         } else {
            this.combinations___init__(iterable, r);
         }
      }
   }

   private void combinations___init__(PyObject iterable, final int r) {
      if (r < 0) {
         throw Py.ValueError("r must be non-negative");
      } else {
         final PyTuple pool = PyTuple.fromIterable(iterable);
         final int n = pool.__len__();
         final int[] indices = new int[r];

         for(int i = 0; i < r; indices[i] = i++) {
         }

         this.iter = new itertools.ItertoolsIterator() {
            boolean firstthru = true;

            public PyObject __iternext__() {
               if (r > n) {
                  return null;
               } else if (this.firstthru) {
                  this.firstthru = false;
                  return itertools.makeIndexedTuple(pool, indices);
               } else {
                  int i;
                  for(i = r - 1; i >= 0 && indices[i] == i + n - r; --i) {
                  }

                  if (i < 0) {
                     return null;
                  } else {
                     int var10002 = indices[i]++;

                     for(int j = i + 1; j < r; ++j) {
                        indices[j] = indices[j - 1] + 1;
                     }

                     return itertools.makeIndexedTuple(pool, indices);
                  }
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
      PyType.addBuilder(combinations.class, new PyExposer());
      TYPE = PyType.fromClass(combinations.class);
   }

   private static class combinations___init___exposer extends PyBuiltinMethod {
      public combinations___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public combinations___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new combinations___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((combinations)this.self).combinations___init__(var1, var2);
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
         return ((combinations)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         combinations var4 = new combinations(this.for_type);
         if (var1) {
            var4.combinations___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new combinationsDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new combinations___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.combinations", combinations.class, PyObject.class, (boolean)1, "combinations(iterable, r) --> combinations object\n\nReturn successive r-length combinations of elements in the iterable.\n\ncombinations(range(4), 3) --> (0,1,2), (0,1,3), (0,2,3), (1,2,3)", var1, var2, new exposed___new__());
      }
   }
}
