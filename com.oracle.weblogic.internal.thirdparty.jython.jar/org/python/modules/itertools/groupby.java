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
import org.python.core.PyXRange;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.groupby",
   base = PyObject.class,
   doc = "groupby(iterable[, keyfunc]) -> create an iterator which returns\n(key, sub-iterator) grouped by each value of key(value)."
)
public class groupby extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String groupby_doc = "groupby(iterable[, keyfunc]) -> create an iterator which returns\n(key, sub-iterator) grouped by each value of key(value).";

   public groupby() {
   }

   public groupby(PyType subType) {
      super(subType);
   }

   public groupby(PyObject iterable) {
      this.groupby___init__(iterable, Py.None);
   }

   public groupby(PyObject iterable, PyObject keyfunc) {
      this.groupby___init__(iterable, keyfunc);
   }

   @ExposedNew
   final void groupby___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("groupby", args, kwds, "iterable", "key");
      if (args.length > 2) {
         throw Py.TypeError("groupby takes two arguments, iterable and key");
      } else {
         PyObject iterable = ap.getPyObject(0);
         PyObject keyfunc = ap.getPyObject(1, Py.None);
         this.groupby___init__(iterable, keyfunc);
      }
   }

   private void groupby___init__(final PyObject iterable, final PyObject keyfunc) {
      this.iter = new itertools.ItertoolsIterator() {
         PyObject currentKey;
         PyObject currentValue;
         PyObject iterator = iterable.__iter__();
         PyObject targetKey;

         {
            this.targetKey = this.currentKey = this.currentValue = new PyXRange(0);
         }

         public PyObject __iternext__() {
            while(this.currentKey.equals(this.targetKey)) {
               this.currentValue = this.nextElement(this.iterator);
               if (this.currentValue == null) {
                  return null;
               }

               if (keyfunc == Py.None) {
                  this.currentKey = this.currentValue;
               } else {
                  this.currentKey = keyfunc.__call__(this.currentValue);
               }
            }

            this.targetKey = this.currentKey;
            return new PyTuple(new PyObject[]{this.currentKey, new null.GroupByIterator()});
         }

         class GroupByIterator extends itertools.ItertoolsIterator {
            private boolean completed = false;

            public PyObject __iternext__() {
               PyObject item = currentValue;
               if (this.completed) {
                  return null;
               } else {
                  currentValue = this.nextElement(iterator);
                  if (currentValue == null) {
                     this.completed = true;
                  } else if (keyfunc == Py.None) {
                     currentKey = currentValue;
                  } else {
                     currentKey = keyfunc.__call__(currentValue);
                  }

                  if (!currentKey.equals(targetKey)) {
                     this.completed = true;
                  }

                  return item;
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
      PyType.addBuilder(groupby.class, new PyExposer());
      TYPE = PyType.fromClass(groupby.class);
   }

   private static class groupby___init___exposer extends PyBuiltinMethod {
      public groupby___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public groupby___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new groupby___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((groupby)this.self).groupby___init__(var1, var2);
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
         return ((groupby)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         groupby var4 = new groupby(this.for_type);
         if (var1) {
            var4.groupby___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new groupbyDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new groupby___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.groupby", groupby.class, PyObject.class, (boolean)1, "groupby(iterable[, keyfunc]) -> create an iterator which returns\n(key, sub-iterator) grouped by each value of key(value).", var1, var2, new exposed___new__());
      }
   }
}
