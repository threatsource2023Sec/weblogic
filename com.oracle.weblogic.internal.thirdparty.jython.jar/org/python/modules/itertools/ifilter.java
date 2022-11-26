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
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.ifilter",
   base = PyObject.class,
   doc = "ifilter(function or None, sequence) --> ifilter object\n\nReturn those items of sequence for which function(item) is true.\nIf function is None, return the items that are true."
)
public class ifilter extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String ifilter_doc = "ifilter(function or None, sequence) --> ifilter object\n\nReturn those items of sequence for which function(item) is true.\nIf function is None, return the items that are true.";

   public ifilter() {
   }

   public ifilter(PyType subType) {
      super(subType);
   }

   public ifilter(PyObject predicate, PyObject iterable) {
      this.ifilter___init__(predicate, iterable);
   }

   @ExposedNew
   final void ifilter___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("ifilter", args, kwds, new String[]{"predicate", "iterable"}, 2);
      ap.noKeywords();
      PyObject predicate = ap.getPyObject(0);
      PyObject iterable = ap.getPyObject(1);
      this.ifilter___init__(predicate, iterable);
   }

   private void ifilter___init__(PyObject predicate, PyObject iterable) {
      this.iter = new itertools.FilterIterator(predicate, iterable, true);
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
      PyType.addBuilder(ifilter.class, new PyExposer());
      TYPE = PyType.fromClass(ifilter.class);
   }

   private static class ifilter___init___exposer extends PyBuiltinMethod {
      public ifilter___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public ifilter___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ifilter___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ifilter)this.self).ifilter___init__(var1, var2);
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
         return ((ifilter)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         ifilter var4 = new ifilter(this.for_type);
         if (var1) {
            var4.ifilter___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ifilterDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new ifilter___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.ifilter", ifilter.class, PyObject.class, (boolean)1, "ifilter(function or None, sequence) --> ifilter object\n\nReturn those items of sequence for which function(item) is true.\nIf function is None, return the items that are true.", var1, var2, new exposed___new__());
      }
   }
}
