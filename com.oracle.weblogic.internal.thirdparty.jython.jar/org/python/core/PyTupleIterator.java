package org.python.core;

import java.util.Iterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "tupleiterator",
   base = PyIterator.class,
   isBaseType = false
)
public class PyTupleIterator extends PyIterator {
   public static final PyType TYPE;
   private Iterator iterator;

   public PyTupleIterator(PyTuple tuple) {
      this.iterator = tuple.getList().iterator();
   }

   public PyObject __iternext__() {
      return !this.iterator.hasNext() ? null : (PyObject)this.iterator.next();
   }

   static {
      PyType.addBuilder(PyTupleIterator.class, new PyExposer());
      TYPE = PyType.fromClass(PyTupleIterator.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("tupleiterator", PyTupleIterator.class, PyIterator.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
