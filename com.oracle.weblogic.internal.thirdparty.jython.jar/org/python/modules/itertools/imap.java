package org.python.modules.itertools;

import org.python.core.Py;
import org.python.core.PyIterator;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.ExposedMethod;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "itertools.imap",
   base = PyObject.class,
   doc = "'map(func, *iterables) --> imap object\n\nMake an iterator that computes the function using arguments from\neach of the iterables.\tLike map() except that it returns\nan iterator instead of a list and that it stops when the shortest\niterable is exhausted instead of filling in None for shorter\niterables."
)
public class imap extends PyIterator {
   public static final PyType TYPE = PyType.fromClass(imap.class);
   private PyIterator iter;
   public static final String imap_doc = "'map(func, *iterables) --> imap object\n\nMake an iterator that computes the function using arguments from\neach of the iterables.\tLike map() except that it returns\nan iterator instead of a list and that it stops when the shortest\niterable is exhausted instead of filling in None for shorter\niterables.";

   public imap() {
   }

   public imap(PyType subType) {
      super(subType);
   }

   public imap(PyObject... args) {
      this.imap___init__(args);
   }

   @ExposedNew
   @ExposedMethod
   final void imap___init__(PyObject[] args, String[] kwds) {
      if (kwds.length > 0) {
         throw Py.TypeError(String.format("imap does not take keyword arguments"));
      } else {
         this.imap___init__(args);
      }
   }

   private void imap___init__(PyObject[] argstar) {
      if (argstar.length < 2) {
         throw Py.TypeError("imap requires at least two arguments");
      } else {
         final int n = argstar.length - 1;
         final PyObject func = argstar[0];
         final PyObject[] iterables = new PyObject[n];

         for(int j = 0; j < n; ++j) {
            iterables[j] = Py.iter(argstar[j + 1], "argument " + (j + 1) + " to imap() must support iteration");
         }

         this.iter = new PyIterator() {
            PyObject[] args = new PyObject[n];
            PyObject element = null;

            public PyObject __iternext__() {
               for(int i = 0; i < n; ++i) {
                  if ((this.element = iterables[i].__iternext__()) == null) {
                     return null;
                  }

                  this.args[i] = this.element;
               }

               if (func == Py.None) {
                  if (n == 1) {
                     return this.args[0];
                  } else {
                     return new PyTuple((PyObject[])this.args.clone());
                  }
               } else {
                  return func.__call__(this.args);
               }
            }
         };
      }
   }

   public PyObject __iternext__() {
      return this.iter.__iternext__();
   }

   @ExposedMethod
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
}
