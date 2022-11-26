package org.python.core;

import org.python.expose.ExposedMethod;
import org.python.expose.ExposedType;

@ExposedType(
   name = "rangeiterator",
   base = PyObject.class,
   isBaseType = false
)
public class PyXRangeIter extends PyIterator {
   public static final PyType TYPE = PyType.fromClass(PyXRangeIter.class);
   private long index;
   private long start;
   private long step;
   private long len;

   public PyXRangeIter(long index, long start, long step, long len) {
      super(TYPE);
      this.index = index;
      this.start = start;
      this.step = step;
      this.len = len;
   }

   @ExposedMethod(
      doc = "x.next() -> the next value, or raise StopIteration"
   )
   final PyObject rangeiterator_next() {
      return super.next();
   }

   public PyObject __iternext__() {
      return this.index < this.len ? Py.newInteger(this.start + this.index++ * this.step) : null;
   }

   static {
      TYPE.setName("rangeiterator");
   }
}
