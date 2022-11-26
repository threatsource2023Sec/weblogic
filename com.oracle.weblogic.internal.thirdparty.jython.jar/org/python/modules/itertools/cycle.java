package org.python.modules.itertools;

import java.util.ArrayList;
import java.util.List;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyIterator;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.count",
   base = PyObject.class,
   doc = "cycle(iterable) --> cycle object\n\nReturn elements from the iterable until it is exhausted.\nThen repeat the sequence indefinitely."
)
public class cycle extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String cycle_doc = "cycle(iterable) --> cycle object\n\nReturn elements from the iterable until it is exhausted.\nThen repeat the sequence indefinitely.";

   public cycle() {
   }

   public cycle(PyType subType) {
      super(subType);
   }

   public cycle(PyObject sequence) {
      this.cycle___init__(sequence);
   }

   @ExposedNew
   final void cycle___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("cycle", args, kwds, new String[]{"iterable"}, 1);
      ap.noKeywords();
      this.cycle___init__(ap.getPyObject(0));
   }

   private void cycle___init__(final PyObject sequence) {
      this.iter = new itertools.ItertoolsIterator() {
         List saved = new ArrayList();
         int counter = 0;
         PyObject iterator = sequence.__iter__();
         boolean save = true;

         public PyObject __iternext__() {
            if (this.save) {
               PyObject obj = this.nextElement(this.iterator);
               if (obj != null) {
                  this.saved.add(obj);
                  return obj;
               }

               this.save = false;
            }

            if (this.saved.size() == 0) {
               return null;
            } else {
               if (this.counter >= this.saved.size()) {
                  this.counter = 0;
               }

               return (PyObject)this.saved.get(this.counter++);
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
      PyType.addBuilder(cycle.class, new PyExposer());
      TYPE = PyType.fromClass(cycle.class);
   }

   private static class cycle___init___exposer extends PyBuiltinMethod {
      public cycle___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public cycle___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new cycle___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((cycle)this.self).cycle___init__(var1, var2);
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
         return ((cycle)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         cycle var4 = new cycle(this.for_type);
         if (var1) {
            var4.cycle___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new cycleDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new cycle___init___exposer("cycle___init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.count", cycle.class, PyObject.class, (boolean)1, "cycle(iterable) --> cycle object\n\nReturn elements from the iterable until it is exhausted.\nThen repeat the sequence indefinitely.", var1, var2, new exposed___new__());
      }
   }
}
