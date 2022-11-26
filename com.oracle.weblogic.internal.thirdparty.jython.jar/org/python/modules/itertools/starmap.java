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
   name = "itertools.starmap",
   base = PyObject.class,
   doc = "starmap(function, sequence) --> starmap object\n\nReturn an iterator whose values are returned from the function evaluated\nwith an argument tuple taken from the given sequence."
)
public class starmap extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String starmap_doc = "starmap(function, sequence) --> starmap object\n\nReturn an iterator whose values are returned from the function evaluated\nwith an argument tuple taken from the given sequence.";

   public starmap() {
   }

   public starmap(PyType subType) {
      super(subType);
   }

   public starmap(PyObject callable, PyObject iterator) {
      this.starmap___init__(callable, iterator);
   }

   @ExposedNew
   final void starmap___init__(PyObject[] starargs, String[] kwds) {
      if (starargs.length != 2) {
         throw Py.TypeError("starmap requires 2 arguments, got " + starargs.length);
      } else {
         PyObject callable = starargs[0];
         PyObject iterator = starargs[1].__iter__();
         this.starmap___init__(callable, iterator);
      }
   }

   private void starmap___init__(final PyObject callable, final PyObject iterator) {
      this.iter = new itertools.ItertoolsIterator() {
         public PyObject __iternext__() {
            PyObject args = this.nextElement(iterator);
            PyObject result = null;
            if (args != null) {
               PyTuple argTuple = PyTuple.fromIterable(args);
               result = callable.__call__(argTuple.getArray());
            }

            return result;
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
      PyType.addBuilder(starmap.class, new PyExposer());
      TYPE = PyType.fromClass(starmap.class);
   }

   private static class starmap___init___exposer extends PyBuiltinMethod {
      public starmap___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public starmap___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new starmap___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((starmap)this.self).starmap___init__(var1, var2);
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
         return ((starmap)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         starmap var4 = new starmap(this.for_type);
         if (var1) {
            var4.starmap___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new starmapDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new starmap___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.starmap", starmap.class, PyObject.class, (boolean)1, "starmap(function, sequence) --> starmap object\n\nReturn an iterator whose values are returned from the function evaluated\nwith an argument tuple taken from the given sequence.", var1, var2, new exposed___new__());
      }
   }
}
