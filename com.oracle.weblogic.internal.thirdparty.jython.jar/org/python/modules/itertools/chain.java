package org.python.modules.itertools;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinClassMethodNarrow;
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
   name = "itertools.chain",
   base = PyObject.class,
   doc = "chain(*iterables) --> chain object\n\nReturn a chain object whose .next() method returns elements from the\nfirst iterable until it is exhausted, then elements from the next\niterable, until all of the iterables are exhausted."
)
public class chain extends PyIterator {
   public static final PyType TYPE;
   private itertools.ItertoolsIterator iter;
   public static final String chain_doc = "chain(*iterables) --> chain object\n\nReturn a chain object whose .next() method returns elements from the\nfirst iterable until it is exhausted, then elements from the next\niterable, until all of the iterables are exhausted.";

   public chain() {
   }

   public chain(PyType subType) {
      super(subType);
   }

   public chain(PyObject iterable) {
      this.chain___init__(iterable.__iter__());
   }

   public static final PyObject from_iterable(PyType type, PyObject iterable) {
      return new chain(iterable);
   }

   @ExposedNew
   final void chain___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("chain", args, kwds, "iterables");
      ap.noKeywords();
      PyTuple tuple = (PyTuple)ap.getList(0);
      this.chain___init__(tuple.__iter__());
   }

   private void chain___init__(final PyObject superIterator) {
      this.iter = new itertools.ItertoolsIterator() {
         int iteratorIndex = 0;
         PyObject currentIterator = new PyObject();

         public PyObject __iternext__() {
            PyObject next = null;

            PyObject nextIterable;
            do {
               next = this.nextElement(this.currentIterator);
            } while(next == null && (nextIterable = this.nextElement(superIterator)) != null && (this.currentIterator = nextIterable.__iter__()) != null);

            return next;
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
      PyType.addBuilder(chain.class, new PyExposer());
      TYPE = PyType.fromClass(chain.class);
   }

   private static class from_iterable_exposer extends PyBuiltinClassMethodNarrow {
      public from_iterable_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public from_iterable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new from_iterable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return chain.from_iterable((PyType)this.self, var1);
      }
   }

   private static class chain___init___exposer extends PyBuiltinMethod {
      public chain___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public chain___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new chain___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((chain)this.self).chain___init__(var1, var2);
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
         return ((chain)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         chain var4 = new chain(this.for_type);
         if (var1) {
            var4.chain___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new chainDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_iterable_exposer("from_iterable"), new chain___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.chain", chain.class, PyObject.class, (boolean)1, "chain(*iterables) --> chain object\n\nReturn a chain object whose .next() method returns elements from the\nfirst iterable until it is exhausted, then elements from the next\niterable, until all of the iterables are exhausted.", var1, var2, new exposed___new__());
      }
   }
}
