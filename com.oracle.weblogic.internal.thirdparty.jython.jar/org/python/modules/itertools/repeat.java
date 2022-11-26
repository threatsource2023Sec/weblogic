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
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.repeat",
   base = PyObject.class,
   doc = "'repeat(element [,times]) -> create an iterator which returns the element\nfor the specified number of times.  If not specified, returns the element\nendlessly."
)
public class repeat extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   private PyObject object;
   private int counter;
   public static final String repeat_doc = "'repeat(element [,times]) -> create an iterator which returns the element\nfor the specified number of times.  If not specified, returns the element\nendlessly.";

   public repeat() {
   }

   public repeat(PyType subType) {
      super(subType);
   }

   public repeat(PyObject object) {
      this.repeat___init__(object);
   }

   public repeat(PyObject object, int times) {
      this.repeat___init__(object, times);
   }

   @ExposedNew
   final void repeat___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("repeat", args, kwds, new String[]{"object", "times"}, 1);
      PyObject object = ap.getPyObject(0);
      if (args.length == 1) {
         this.repeat___init__(object);
      } else {
         int times = ap.getInt(1);
         this.repeat___init__(object, times);
      }

   }

   private void repeat___init__(final PyObject object, int times) {
      this.object = object;
      if (times < 0) {
         this.counter = 0;
      } else {
         this.counter = times;
      }

      this.iter = new PyIterator() {
         public PyObject __iternext__() {
            if (repeat.this.counter > 0) {
               repeat.this.counter--;
               return object;
            } else {
               return null;
            }
         }
      };
   }

   private void repeat___init__(final PyObject object) {
      this.object = object;
      this.counter = -1;
      this.iter = new PyIterator() {
         public PyObject __iternext__() {
            return object;
         }
      };
   }

   final PyObject __copy__() {
      return new repeat(this.object, this.counter);
   }

   public int __len__() {
      if (this.counter < 0) {
         throw Py.TypeError("object of type 'itertools.repeat' has no len()");
      } else {
         return this.counter;
      }
   }

   public PyString __repr__() {
      return this.counter >= 0 ? (PyString)((PyString)Py.newString("repeat(%r, %d)").__mod__(new PyTuple(new PyObject[]{this.object, Py.newInteger(this.counter)}))) : (PyString)((PyString)Py.newString("repeat(%r)").__mod__(new PyTuple(new PyObject[]{this.object})));
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
         if (this.object != null) {
            retVal = visit.visit(this.object, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.iter != null ? visit.visit(this.iter, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.iter == ob || this.object == ob || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(repeat.class, new PyExposer());
      TYPE = PyType.fromClass(repeat.class);
   }

   private static class repeat___init___exposer extends PyBuiltinMethod {
      public repeat___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public repeat___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new repeat___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((repeat)this.self).repeat___init__(var1, var2);
         return Py.None;
      }
   }

   private static class __copy___exposer extends PyBuiltinMethodNarrow {
      public __copy___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __copy___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __copy___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((repeat)this.self).__copy__();
      }
   }

   private static class __len___exposer extends PyBuiltinMethodNarrow {
      public __len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((repeat)this.self).__len__());
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
         return ((repeat)this.self).__repr__();
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
         return ((repeat)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         repeat var4 = new repeat(this.for_type);
         if (var1) {
            var4.repeat___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new repeatDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new repeat___init___exposer("__init__"), new __copy___exposer("__copy__"), new __len___exposer("__len__"), new __repr___exposer("__repr__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.repeat", repeat.class, PyObject.class, (boolean)1, "'repeat(element [,times]) -> create an iterator which returns the element\nfor the specified number of times.  If not specified, returns the element\nendlessly.", var1, var2, new exposed___new__());
      }
   }
}
