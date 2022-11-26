package org.python.modules.itertools;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyIterator;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.core.__builtin__;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.count",
   base = PyObject.class,
   doc = "count(start=0, step=1) --> count object\n\nReturn a count object whose .next() method returns consecutive values.\n  Equivalent to:\n\n      def count(firstval=0, step=1):\n      x = firstval\n      while 1:\n          yield x\n          x += step\n"
)
public class count extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   private PyObject counter;
   private PyObject stepper;
   private static PyObject NumberClass;
   public static final String count_doc = "count(start=0, step=1) --> count object\n\nReturn a count object whose .next() method returns consecutive values.\n  Equivalent to:\n\n      def count(firstval=0, step=1):\n      x = firstval\n      while 1:\n          yield x\n          x += step\n";

   private static synchronized PyObject getNumberClass() {
      if (NumberClass == null) {
         NumberClass = __builtin__.__import__("numbers").__getattr__("Number");
      }

      return NumberClass;
   }

   public count(PyType subType) {
      super(subType);
   }

   public count() {
      this.count___init__((PyObject)Py.Zero, (PyObject)Py.One);
   }

   public count(PyObject start) {
      this.count___init__((PyObject)start, (PyObject)Py.One);
   }

   public count(PyObject start, PyObject step) {
      this.count___init__(start, step);
   }

   private static PyObject getNumber(PyObject obj) {
      if (Py.isInstance(obj, getNumberClass())) {
         return obj;
      } else {
         try {
            PyObject intObj = obj.__int__();
            if (Py.isInstance(obj, getNumberClass())) {
               return intObj;
            } else {
               throw Py.TypeError("a number is required");
            }
         } catch (PyException var2) {
            if (var2.match(Py.ValueError)) {
               throw Py.TypeError("a number is required");
            } else {
               throw var2;
            }
         }
      }
   }

   @ExposedNew
   final void count___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("count", args, kwds, new String[]{"start", "step"}, 0);
      PyObject start = getNumber(ap.getPyObject(0, Py.Zero));
      PyObject step = getNumber(ap.getPyObject(1, Py.One));
      this.count___init__(start, step);
   }

   private void count___init__(PyObject start, PyObject step) {
      this.counter = start;
      this.stepper = step;
      this.iter = new PyIterator() {
         public PyObject __iternext__() {
            PyObject result = count.this.counter;
            count.this.counter = count.this.counter._add(count.this.stepper);
            return result;
         }
      };
   }

   public PyObject count___copy__() {
      return new count(this.counter, this.stepper);
   }

   final PyObject count___reduce_ex__(PyObject protocol) {
      return this.__reduce_ex__(protocol);
   }

   final PyObject count___reduce__() {
      return this.__reduce_ex__(Py.Zero);
   }

   public PyObject __reduce_ex__(PyObject protocol) {
      return this.stepper == Py.One ? new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{this.counter})}) : new PyTuple(new PyObject[]{this.getType(), new PyTuple(new PyObject[]{this.counter, this.stepper})});
   }

   public PyString __repr__() {
      return this.stepper instanceof PyInteger && this.stepper._cmp(Py.One) == 0 ? Py.newString(String.format("count(%s)", this.counter)) : Py.newString(String.format("count(%s, %s)", this.counter, this.stepper));
   }

   public PyObject __iternext__() {
      return this.iter.__iternext__();
   }

   public PyObject next() {
      return this.doNext(this.__iternext__());
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.iter != null) {
         retVal = visit.visit(this.iter, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.counter != null) {
         retVal = visit.visit(this.counter, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.stepper != null) {
         retVal = visit.visit(this.stepper, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return super.traverse(visit, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.iter == ob || this.counter == ob || this.stepper == ob || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(count.class, new PyExposer());
      TYPE = PyType.fromClass(count.class);
   }

   private static class count___init___exposer extends PyBuiltinMethod {
      public count___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public count___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new count___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((count)this.self).count___init__(var1, var2);
         return Py.None;
      }
   }

   private static class count___copy___exposer extends PyBuiltinMethodNarrow {
      public count___copy___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public count___copy___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new count___copy___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((count)this.self).count___copy__();
      }
   }

   private static class count___reduce_ex___exposer extends PyBuiltinMethodNarrow {
      public count___reduce_ex___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public count___reduce_ex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new count___reduce_ex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((count)this.self).count___reduce_ex__(var1);
      }
   }

   private static class count___reduce___exposer extends PyBuiltinMethodNarrow {
      public count___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public count___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new count___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((count)this.self).count___reduce__();
      }
   }

   private static class __repr___exposer extends PyBuiltinMethodNarrow {
      public __repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((count)this.self).__repr__();
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
         return ((count)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         count var4 = new count(this.for_type);
         if (var1) {
            var4.count___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new countDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new count___init___exposer("__init__"), new count___copy___exposer("__copy__"), new count___reduce_ex___exposer("__reduce_ex__"), new count___reduce___exposer("__reduce__"), new __repr___exposer("__repr__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.count", count.class, PyObject.class, (boolean)1, "count(start=0, step=1) --> count object\n\nReturn a count object whose .next() method returns consecutive values.\n  Equivalent to:\n\n      def count(firstval=0, step=1):\n      x = firstval\n      while 1:\n          yield x\n          x += step\n", var1, var2, new exposed___new__());
      }
   }
}
