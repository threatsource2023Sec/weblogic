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
   name = "itertools.izip_longest",
   base = PyObject.class,
   doc = "izip_longest(iter1 [,iter2 [...]], [fillvalue=None]) --> izip_longest object\n\nReturn an izip_longest object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the longest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  When the shorter iterables\nare exhausted, the fillvalue is substituted in their place.  The fillvalue\ndefaults to None or can be specified by a keyword argument."
)
public class izipLongest extends PyIterator {
   public static final PyType TYPE;
   private PyIterator iter;
   public static final String izip_longest_doc = "izip_longest(iter1 [,iter2 [...]], [fillvalue=None]) --> izip_longest object\n\nReturn an izip_longest object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the longest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  When the shorter iterables\nare exhausted, the fillvalue is substituted in their place.  The fillvalue\ndefaults to None or can be specified by a keyword argument.";

   public izipLongest() {
   }

   public izipLongest(PyType subType) {
      super(subType);
   }

   public izipLongest(PyObject[] iterables, PyObject fillvalue) {
      this.izipLongest___init__(iterables, fillvalue);
   }

   @ExposedNew
   final void izipLongest___init__(PyObject[] args, String[] kwds) {
      PyObject fillvalue;
      PyObject[] iterables;
      if (kwds.length == 1 && kwds[0] == "fillvalue") {
         fillvalue = args[args.length - 1];
         iterables = new PyObject[args.length - 1];
         System.arraycopy(args, 0, iterables, 0, args.length - 1);
      } else {
         fillvalue = Py.None;
         iterables = args;
      }

      this.izipLongest___init__(iterables, fillvalue);
   }

   private void izipLongest___init__(final PyObject[] iterables, final PyObject fillvalue) {
      final PyObject[] iterators = new PyObject[iterables.length];
      final boolean[] exhausted = new boolean[iterables.length];

      for(int i = 0; i < iterables.length; ++i) {
         iterators[i] = iterables[i].__iter__();
         exhausted[i] = false;
      }

      this.iter = new itertools.ItertoolsIterator() {
         int unexhausted = iterables.length;

         public PyObject __iternext__() {
            PyObject[] item = new PyObject[iterables.length];

            for(int i = 0; i < iterables.length; ++i) {
               if (exhausted[i]) {
                  item[i] = fillvalue;
               } else {
                  PyObject elem = iterators[i].__iternext__();
                  if (elem == null) {
                     --this.unexhausted;
                     exhausted[i] = true;
                     item[i] = fillvalue;
                  } else {
                     item[i] = elem;
                  }
               }
            }

            if (this.unexhausted == 0) {
               return null;
            } else {
               return new PyTuple(item);
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
      PyType.addBuilder(izipLongest.class, new PyExposer());
      TYPE = PyType.fromClass(izipLongest.class);
   }

   private static class izipLongest___init___exposer extends PyBuiltinMethod {
      public izipLongest___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public izipLongest___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new izipLongest___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((izipLongest)this.self).izipLongest___init__(var1, var2);
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
         return ((izipLongest)this.self).next();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         izipLongest var4 = new izipLongest(this.for_type);
         if (var1) {
            var4.izipLongest___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new izipLongestDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new izipLongest___init___exposer("izipLongest___init__"), new next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("itertools.izip_longest", izipLongest.class, PyObject.class, (boolean)1, "izip_longest(iter1 [,iter2 [...]], [fillvalue=None]) --> izip_longest object\n\nReturn an izip_longest object whose .next() method returns a tuple where\nthe i-th element comes from the i-th iterable argument.  The .next()\nmethod continues until the longest iterable in the argument sequence\nis exhausted and then it raises StopIteration.  When the shorter iterables\nare exhausted, the fillvalue is substituted in their place.  The fillvalue\ndefaults to None or can be specified by a keyword argument.", var1, var2, new exposed___new__());
      }
   }
}
