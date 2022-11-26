package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "fastsequenceiterator",
   base = PyObject.class,
   isBaseType = false
)
public class PyFastSequenceIter extends PyIterator {
   public static final PyType TYPE;
   private PySequence seq;
   private int index;

   public PyFastSequenceIter(PySequence seq) {
      super(TYPE);
      this.seq = seq;
   }

   final PyObject fastsequenceiterator_next() {
      return super.next();
   }

   public PyObject __iternext__() {
      if (this.seq == null) {
         return null;
      } else {
         PyObject result;
         try {
            result = this.seq.seq___finditem__(this.index++);
         } catch (PyException var3) {
            if (var3.match(Py.StopIteration)) {
               this.seq = null;
               return null;
            }

            throw var3;
         }

         if (result == null) {
            this.seq = null;
         }

         return result;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue = super.traverse(visit, arg);
      if (retValue != 0) {
         return retValue;
      } else {
         return this.seq == null ? 0 : visit.visit(this.seq, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.seq || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyFastSequenceIter.class, new PyExposer());
      TYPE = PyType.fromClass(PyFastSequenceIter.class);
   }

   private static class fastsequenceiterator_next_exposer extends PyBuiltinMethodNarrow {
      public fastsequenceiterator_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public fastsequenceiterator_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new fastsequenceiterator_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFastSequenceIter)this.self).fastsequenceiterator_next();
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new fastsequenceiterator_next_exposer("next")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("fastsequenceiterator", PyFastSequenceIter.class, PyObject.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
