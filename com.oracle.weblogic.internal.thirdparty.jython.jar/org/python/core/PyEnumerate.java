package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "enumerate",
   base = PyObject.class,
   doc = "enumerate(iterable[, start]) -> iterator for index, value of iterable\n\nReturn an enumerate object.  iterable must be another object that supports\niteration.  The enumerate object yields pairs containing a count (from\nstart, which defaults to zero) and a value yielded by the iterable argument.\nenumerate is useful for obtaining an indexed list:\n    (0, seq[0]), (1, seq[1]), (2, seq[2]), ..."
)
public class PyEnumerate extends PyIterator {
   public static final PyType TYPE;
   private PyObject index;
   private PyObject sit;

   public PyEnumerate(PyType subType) {
      super(subType);
   }

   public PyEnumerate(PyType subType, PyObject seq, PyObject start) {
      super(subType);
      this.index = start;
      this.sit = seq.__iter__();
   }

   public PyEnumerate(PyObject seq, PyObject start) {
      this(TYPE, seq, start);
   }

   public PyObject next() {
      return this.enumerate_next();
   }

   final PyObject enumerate_next() {
      return this.doNext(this.enumerate___iternext__());
   }

   final PyObject enumerate___iter__() {
      return super.__iter__();
   }

   @ExposedNew
   public static final PyObject enumerate_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      if (args.length <= 2 && args.length > 0) {
         ArgParser ap = new ArgParser("enumerate", args, keywords, new String[]{"sequence", "start"});
         PyObject seq = ap.getPyObject(0);
         PyObject start = ap.getPyObject(1, Py.newInteger(0));
         if (!start.isIndex()) {
            throw Py.TypeError("an integer is required");
         } else {
            return (PyObject)(new_.for_type == subtype ? new PyEnumerate(seq, start) : new PyEnumerateDerived(subtype, seq, start));
         }
      } else {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(args.length, true, "enumerate", 1, 2);
      }
   }

   public PyObject __iternext__() {
      return this.enumerate___iternext__();
   }

   final PyObject enumerate___iternext__() {
      PyObject nextItem = this.sit.__iternext__();
      if (nextItem == null) {
         if (this.sit instanceof PyIterator && ((PyIterator)this.sit).stopException != null) {
            this.stopException = ((PyIterator)this.sit).stopException;
         }

         return null;
      } else {
         PyObject next = new PyTuple(new PyObject[]{this.index, nextItem});
         this.index = this.index.__radd__(Py.newInteger(1));
         return next;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue = super.traverse(visit, arg);
      if (retValue != 0) {
         return retValue;
      } else {
         if (this.index != null) {
            retValue = visit.visit(this.index, arg);
            if (retValue != 0) {
               return retValue;
            }
         }

         return this.sit == null ? 0 : visit.visit(this.sit, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.index || ob == this.sit || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyEnumerate.class, new PyExposer());
      TYPE = PyType.fromClass(PyEnumerate.class);
   }

   private static class enumerate_next_exposer extends PyBuiltinMethodNarrow {
      public enumerate_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public enumerate_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new enumerate_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyEnumerate)this.self).enumerate_next();
      }
   }

   private static class enumerate___iter___exposer extends PyBuiltinMethodNarrow {
      public enumerate___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public enumerate___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new enumerate___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyEnumerate)this.self).enumerate___iter__();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyEnumerate.enumerate_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new enumerate_next_exposer("next"), new enumerate___iter___exposer("__iter__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("enumerate", PyEnumerate.class, PyObject.class, (boolean)1, "enumerate(iterable[, start]) -> iterator for index, value of iterable\n\nReturn an enumerate object.  iterable must be another object that supports\niteration.  The enumerate object yields pairs containing a count (from\nstart, which defaults to zero) and a value yielded by the iterable argument.\nenumerate is useful for obtaining an indexed list:\n    (0, seq[0]), (1, seq[1]), (2, seq[2]), ...", var1, var2, new exposed___new__());
      }
   }
}
