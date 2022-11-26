package org.python.modules.itertools;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyInteger;
import org.python.core.PyIterator;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.islice",
   base = PyObject.class,
   doc = "islice(iterable, [start,] stop [, step]) --> islice object\n\nReturn an iterator whose next() method returns selected values from an\niterable.  If start is specified, will skip all preceding elements;\notherwise, start defaults to zero.  Step defaults to one.  If\nspecified as another value, step determines how many values are \nskipped between successive calls.  Works like a slice() on a list\nbut returns an iterator."
)
public class islice extends PyIterator {
   public static final PyType TYPE;
   private itertools.ItertoolsIterator iter;
   public static final String islice_doc = "islice(iterable, [start,] stop [, step]) --> islice object\n\nReturn an iterator whose next() method returns selected values from an\niterable.  If start is specified, will skip all preceding elements;\notherwise, start defaults to zero.  Step defaults to one.  If\nspecified as another value, step determines how many values are \nskipped between successive calls.  Works like a slice() on a list\nbut returns an iterator.";

   public islice() {
   }

   public islice(PyType subType) {
      super(subType);
   }

   public islice(PyObject iterable, PyObject stopObj) {
      this.islice___init__(iterable, new PyInteger(0), stopObj, new PyInteger(1));
   }

   public islice(PyObject iterable, PyObject start, PyObject stopObj) {
      this.islice___init__(iterable, start, stopObj, new PyInteger(1));
   }

   @ExposedNew
   final void islice___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("islice", args, kwds, new String[]{"iterable", "start", "stop", "step"}, 2);
      PyObject iterable = ap.getPyObject(0);
      PyObject startObj;
      if (args.length == 2) {
         startObj = ap.getPyObject(1);
         this.islice___init__(iterable, new PyInteger(0), startObj, new PyInteger(1));
      } else {
         startObj = ap.getPyObject(1);
         PyObject stopObj = ap.getPyObject(2);
         if (args.length == 3) {
            this.islice___init__(iterable, startObj, stopObj, new PyInteger(1));
         } else {
            PyObject stepObj = ap.getPyObject(3);
            this.islice___init__(iterable, startObj, stopObj, stepObj);
         }
      }

   }

   private void islice___init__(final PyObject iterable, PyObject startObj, PyObject stopObj, PyObject stepObj) {
      final int start = itertools.py2int(startObj, 0, "Start argument must be a non-negative integer or None");
      final int step = itertools.py2int(stepObj, 1, "Step argument must be a non-negative integer or None");
      int stopArg = itertools.py2int(stopObj, 0, "Stop argument must be a non-negative integer or None");
      final int stop = stopObj instanceof PyNone ? Integer.MAX_VALUE : stopArg;
      if (start >= 0 && step >= 0 && stop >= 0) {
         if (step == 0) {
            throw Py.ValueError("Step must be one or larger for islice()");
         } else {
            this.iter = new itertools.ItertoolsIterator() {
               int counter = start;
               int lastCount = 0;
               PyObject iter = iterable.__iter__();

               public PyObject __iternext__() {
                  PyObject result;
                  for(result = null; this.lastCount < Math.min(this.counter + 1, stop); ++this.lastCount) {
                     result = this.nextElement(this.iter);
                  }

                  if (this.lastCount - 1 == this.counter) {
                     this.counter += step;
                     return result;
                  } else {
                     return null;
                  }
               }
            };
         }
      } else {
         throw Py.ValueError("Indices for islice() must be non-negative integers");
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
      PyType.addBuilder(islice.class, new PyExposer());
      TYPE = PyType.fromClass(islice.class);
   }

   private static class islice___init___exposer extends PyBuiltinMethod {
      public islice___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public islice___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new islice___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((islice)this.self).islice___init__(var1, var2);
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
         return ((islice)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         islice var4 = new islice(this.for_type);
         if (var1) {
            var4.islice___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new isliceDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new islice___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.islice", islice.class, PyObject.class, (boolean)1, "islice(iterable, [start,] stop [, step]) --> islice object\n\nReturn an iterator whose next() method returns selected values from an\niterable.  If start is specified, will skip all preceding elements;\notherwise, start defaults to zero.  Step defaults to one.  If\nspecified as another value, step determines how many values are \nskipped between successive calls.  Works like a slice() on a list\nbut returns an iterator.", var1, var2, new exposed___new__());
      }
   }
}
