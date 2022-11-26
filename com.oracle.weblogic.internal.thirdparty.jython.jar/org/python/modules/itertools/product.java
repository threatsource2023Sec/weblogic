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
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.product",
   base = PyObject.class,
   doc = "product(*iterables) --> product object\n\nCartesian product of input iterables.  Equivalent to nested for-loops.\n\nFor example, product(A, B) returns the same as:  ((x,y) for x in A for y in B).\nThe leftmost iterators are in the outermost for-loop, so the output tuples\ncycle in a manner similar to an odometer (with the rightmost element changing\non every iteration).\n\nTo compute the product of an iterable with itself, specify the number\nof repetitions with the optional repeat keyword argument. For example,\nproduct(A, repeat=4) means the same as product(A, A, A, A).\n\nproduct('ab', range(3)) --> ('a',0) ('a',1) ('a',2) ('b',0) ('b',1) ('b',2)\nproduct((0,1), (0,1), (0,1)) --> (0,0,0) (0,0,1) (0,1,0) (0,1,1) (1,0,0) ..."
)
public class product extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String product_doc = "product(*iterables) --> product object\n\nCartesian product of input iterables.  Equivalent to nested for-loops.\n\nFor example, product(A, B) returns the same as:  ((x,y) for x in A for y in B).\nThe leftmost iterators are in the outermost for-loop, so the output tuples\ncycle in a manner similar to an odometer (with the rightmost element changing\non every iteration).\n\nTo compute the product of an iterable with itself, specify the number\nof repetitions with the optional repeat keyword argument. For example,\nproduct(A, repeat=4) means the same as product(A, A, A, A).\n\nproduct('ab', range(3)) --> ('a',0) ('a',1) ('a',2) ('b',0) ('b',1) ('b',2)\nproduct((0,1), (0,1), (0,1)) --> (0,0,0) (0,0,1) (0,1,0) (0,1,1) (1,0,0) ...";

   public product() {
   }

   public product(PyType subType) {
      super(subType);
   }

   public product(PyTuple[] tuples, int repeat) {
      this.product___init__(tuples, repeat);
   }

   @ExposedNew
   final void product___init__(PyObject[] args, String[] kws) {
      int repeat;
      int num_iterables;
      if (kws.length == 1 && kws[0] == "repeat") {
         repeat = args[args.length - 1].asInt();
         if (repeat < 0) {
            throw Py.ValueError("repeat argument cannot be negative");
         }

         num_iterables = args.length - 1;
      } else {
         repeat = 1;
         num_iterables = args.length;
      }

      PyTuple[] tuples = new PyTuple[num_iterables];

      for(int i = 0; i < num_iterables; ++i) {
         tuples[i] = PyTuple.fromIterable(args[i]);
      }

      this.product___init__(tuples, repeat);
   }

   private void product___init__(PyTuple[] tuples, int repeat) {
      final int num_pools = tuples.length * repeat;
      final PyTuple[] pools = new PyTuple[num_pools];

      for(int r = 0; r < repeat; ++r) {
         System.arraycopy(tuples, 0, pools, r * tuples.length, tuples.length);
      }

      final int[] indices = new int[num_pools];
      this.iter = new itertools.ItertoolsIterator() {
         boolean firstthru = true;

         public PyObject __iternext__() {
            if (this.firstthru) {
               PyTuple[] var5 = pools;
               int var2 = var5.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  PyTuple pool = var5[var3];
                  if (pool.__len__() == 0) {
                     return null;
                  }
               }

               this.firstthru = false;
               return this.makeTuple();
            } else {
               for(int i = num_pools - 1; i >= 0; --i) {
                  int var10002 = indices[i]++;
                  if (indices[i] != pools[i].__len__()) {
                     return this.makeTuple();
                  }

                  indices[i] = 0;
               }

               return null;
            }
         }

         private PyTuple makeTuple() {
            PyObject[] items = new PyObject[num_pools];

            for(int i = 0; i < num_pools; ++i) {
               items[i] = pools[i].__getitem__(indices[i]);
            }

            return new PyTuple(items);
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
      PyType.addBuilder(product.class, new PyExposer());
      TYPE = PyType.fromClass(product.class);
   }

   private static class product___init___exposer extends PyBuiltinMethod {
      public product___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public product___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new product___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((product)this.self).product___init__(var1, var2);
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
         return ((product)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         product var4 = new product(this.for_type);
         if (var1) {
            var4.product___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new productDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new product___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.product", product.class, PyObject.class, (boolean)1, "product(*iterables) --> product object\n\nCartesian product of input iterables.  Equivalent to nested for-loops.\n\nFor example, product(A, B) returns the same as:  ((x,y) for x in A for y in B).\nThe leftmost iterators are in the outermost for-loop, so the output tuples\ncycle in a manner similar to an odometer (with the rightmost element changing\non every iteration).\n\nTo compute the product of an iterable with itself, specify the number\nof repetitions with the optional repeat keyword argument. For example,\nproduct(A, repeat=4) means the same as product(A, A, A, A).\n\nproduct('ab', range(3)) --> ('a',0) ('a',1) ('a',2) ('b',0) ('b',1) ('b',2)\nproduct((0,1), (0,1), (0,1)) --> (0,0,0) (0,0,1) (0,1,0) (0,1,1) (1,0,0) ...", var1, var2, new exposed___new__());
      }
   }
}
