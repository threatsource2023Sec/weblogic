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
   name = "itertools.combinations_with_replacement",
   base = PyObject.class,
   doc = "combinations_with_replacement(iterable, r) --> combinations_with_replacement object\n\nReturn successive r-length combinations of elements in the iterable\nallowing individual elements to have successive repeats.\ncombinations_with_replacement('ABC', 2) --> AA AB AC BB BC CC"
)
public class combinationsWithReplacement extends PyIterator {
   public static final PyType TYPE;
   private itertools.ItertoolsIterator iter;
   public static final String combinations_with_replacement_doc = "combinations_with_replacement(iterable, r) --> combinations_with_replacement object\n\nReturn successive r-length combinations of elements in the iterable\nallowing individual elements to have successive repeats.\ncombinations_with_replacement('ABC', 2) --> AA AB AC BB BC CC";

   public combinationsWithReplacement() {
   }

   public combinationsWithReplacement(PyType subType) {
      super(subType);
   }

   public combinationsWithReplacement(PyObject iterable, int r) {
      this.combinationsWithReplacement___init__(iterable, r);
   }

   @ExposedNew
   final void combinationsWithReplacement___init__(PyObject[] args, String[] kwds) {
      if (args.length > 2) {
         throw Py.TypeError("combinations_with_replacement() takes at most 2 arguments (3 given)");
      } else {
         ArgParser ap = new ArgParser("combinations_with_replacement", args, kwds, "iterable", "r");
         PyObject iterable = ap.getPyObject(0);
         int r = ap.getInt(1);
         if (r < 0) {
            throw Py.ValueError("r must be non-negative");
         } else {
            this.combinationsWithReplacement___init__(iterable, r);
         }
      }
   }

   private void combinationsWithReplacement___init__(PyObject iterable, final int r) {
      final PyTuple pool = PyTuple.fromIterable(iterable);
      final int n = pool.__len__();
      final int[] indices = new int[r];

      for(int i = 0; i < r; ++i) {
         indices[i] = 0;
      }

      this.iter = new itertools.ItertoolsIterator() {
         boolean firstthru = true;

         public PyObject __iternext__() {
            if (this.firstthru) {
               this.firstthru = false;
               return n == 0 && r > 0 ? null : itertools.makeIndexedTuple(pool, indices);
            } else {
               int i;
               for(i = r - 1; i >= 0 && indices[i] == n - 1; --i) {
               }

               if (i < 0) {
                  return null;
               } else {
                  int var10002 = indices[i]++;

                  for(int j = i + 1; j < r; ++j) {
                     indices[j] = indices[j - 1];
                  }

                  return itertools.makeIndexedTuple(pool, indices);
               }
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
      PyType.addBuilder(combinationsWithReplacement.class, new PyExposer());
      TYPE = PyType.fromClass(combinationsWithReplacement.class);
   }

   private static class combinationsWithReplacement___init___exposer extends PyBuiltinMethod {
      public combinationsWithReplacement___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public combinationsWithReplacement___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new combinationsWithReplacement___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((combinationsWithReplacement)this.self).combinationsWithReplacement___init__(var1, var2);
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
         return ((combinationsWithReplacement)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         combinationsWithReplacement var4 = new combinationsWithReplacement(this.for_type);
         if (var1) {
            var4.combinationsWithReplacement___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new combinationsWithReplacementDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new combinationsWithReplacement___init___exposer("combinationsWithReplacement___init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.combinations_with_replacement", combinationsWithReplacement.class, PyObject.class, (boolean)1, "combinations_with_replacement(iterable, r) --> combinations_with_replacement object\n\nReturn successive r-length combinations of elements in the iterable\nallowing individual elements to have successive repeats.\ncombinations_with_replacement('ABC', 2) --> AA AB AC BB BC CC", var1, var2, new exposed___new__());
      }
   }
}
