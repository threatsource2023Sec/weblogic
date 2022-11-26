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
   name = "itertools.permutations",
   base = PyObject.class,
   doc = "permutations(iterable[, r]) --> permutations object\n\nReturn successive r-length permutations of elements in the iterable.\n\npermutations(range(3), 2) --> (0,1), (0,2), (1,0), (1,2), (2,0), (2,1)"
)
public class permutations extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String permutations_doc = "permutations(iterable[, r]) --> permutations object\n\nReturn successive r-length permutations of elements in the iterable.\n\npermutations(range(3), 2) --> (0,1), (0,2), (1,0), (1,2), (2,0), (2,1)";

   public permutations() {
   }

   public permutations(PyType subType) {
      super(subType);
   }

   public permutations(PyObject iterable, int r) {
      this.permutations___init__(iterable, r);
   }

   @ExposedNew
   final void permutations___init__(PyObject[] args, String[] kwds) {
      if (args.length > 2) {
         throw Py.TypeError("permutations() takes at most 2 arguments (3 given)");
      } else {
         ArgParser ap = new ArgParser("permutations", args, kwds, "iterable", "r");
         PyObject iterable = ap.getPyObject(0);
         PyObject r = ap.getPyObject(1, Py.None);
         int perm_length;
         if (r == Py.None) {
            perm_length = iterable.__len__();
         } else {
            perm_length = r.asInt();
            if (perm_length < 0) {
               throw Py.ValueError("r must be non-negative");
            }
         }

         this.permutations___init__(iterable, perm_length);
      }
   }

   private void permutations___init__(PyObject iterable, final int r) {
      final PyTuple pool = PyTuple.fromIterable(iterable);
      final int n = pool.__len__();
      final int[] indices = new int[n];

      for(int i = 0; i < n; indices[i] = i++) {
      }

      final int[] cycles = new int[r];

      for(int i = 0; i < r; ++i) {
         cycles[i] = n - i;
      }

      this.iter = new itertools.ItertoolsIterator() {
         boolean firstthru = true;

         public PyObject __iternext__() {
            if (r > n) {
               return null;
            } else if (this.firstthru) {
               this.firstthru = false;
               return itertools.makeIndexedTuple(pool, indices, r);
            } else {
               for(int i = r - 1; i >= 0; --i) {
                  int var10002 = cycles[i]--;
                  int first;
                  int j;
                  if (cycles[i] != 0) {
                     first = cycles[i];
                     j = indices[i];
                     indices[i] = indices[n - first];
                     indices[n - first] = j;
                     return itertools.makeIndexedTuple(pool, indices, r);
                  }

                  first = indices[i];

                  for(j = i; j < n - 1; ++j) {
                     indices[j] = indices[j + 1];
                  }

                  indices[n - 1] = first;
                  cycles[i] = n - i;
               }

               return null;
            }
         }
      };
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
      PyType.addBuilder(permutations.class, new PyExposer());
      TYPE = PyType.fromClass(permutations.class);
   }

   private static class permutations___init___exposer extends PyBuiltinMethod {
      public permutations___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public permutations___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new permutations___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((permutations)this.self).permutations___init__(var1, var2);
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
         return ((permutations)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         permutations var4 = new permutations(this.for_type);
         if (var1) {
            var4.permutations___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new permutationsDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new permutations___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.permutations", permutations.class, PyObject.class, (boolean)1, "permutations(iterable[, r]) --> permutations object\n\nReturn successive r-length permutations of elements in the iterable.\n\npermutations(range(3), 2) --> (0,1), (0,2), (1,0), (1,2), (2,0), (2,1)", var1, var2, new exposed___new__());
      }
   }
}
