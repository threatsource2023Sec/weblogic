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
   name = "itertools.compress",
   base = PyObject.class,
   doc = "compress(data, selectors) --> iterator over selected data\n\nReturn data elements corresponding to true selector elements.\nForms a shorter iterator from selected data elements using the\nselectors to choose the data elements."
)
public class compress extends PyIterator {
   public static final PyType TYPE;
   private itertools.ItertoolsIterator iter;
   public static final String compress_doc = "compress(data, selectors) --> iterator over selected data\n\nReturn data elements corresponding to true selector elements.\nForms a shorter iterator from selected data elements using the\nselectors to choose the data elements.";

   public compress() {
   }

   public compress(PyType subType) {
      super(subType);
   }

   public compress(PyObject dataIterable, PyObject selectorsIterable) {
      this.compress___init__(dataIterable.__iter__(), selectorsIterable.__iter__());
   }

   @ExposedNew
   final void compress___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("compress", args, kwds, "data", "selectors");
      if (args.length > 2) {
         throw Py.TypeError(String.format("compress() takes at most 2 arguments (%s given)", args.length));
      } else {
         PyObject data = ap.getPyObject(0).__iter__();
         PyObject selectors = ap.getPyObject(1).__iter__();
         this.compress___init__(data, selectors);
      }
   }

   private void compress___init__(final PyObject data, final PyObject selectors) {
      this.iter = new itertools.ItertoolsIterator() {
         public PyObject __iternext__() {
            PyObject datum;
            PyObject selector;
            do {
               datum = this.nextElement(data);
               if (datum == null) {
                  return null;
               }

               selector = this.nextElement(selectors);
               if (selector == null) {
                  return null;
               }
            } while(!selector.__nonzero__());

            return datum;
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
      PyType.addBuilder(compress.class, new PyExposer());
      TYPE = PyType.fromClass(compress.class);
   }

   private static class compress___init___exposer extends PyBuiltinMethod {
      public compress___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public compress___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new compress___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((compress)this.self).compress___init__(var1, var2);
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
         return ((compress)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         compress var4 = new compress(this.for_type);
         if (var1) {
            var4.compress___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new compressDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new compress___init___exposer("__init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.compress", compress.class, PyObject.class, (boolean)1, "compress(data, selectors) --> iterator over selected data\n\nReturn data elements corresponding to true selector elements.\nForms a shorter iterator from selected data elements using the\nselectors to choose the data elements.", var1, var2, new exposed___new__());
      }
   }
}
