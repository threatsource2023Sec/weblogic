package org.python.core;

import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "sys.long_info",
   isBaseType = false
)
class LongInfo extends PyTuple {
   @ExposedGet
   public PyObject bits_per_digit;
   @ExposedGet
   public PyObject sizeof_digit;
   public static final PyType TYPE = PyType.fromClass(LongInfo.class);

   private LongInfo(PyObject... vals) {
      super(TYPE, vals);
      this.bits_per_digit = vals[0];
      this.sizeof_digit = vals[1];
   }

   public static LongInfo getInfo() {
      return new LongInfo(new PyObject[]{Py.newLong(30), Py.newLong(4)});
   }

   public PyString __repr__() {
      return (PyString)Py.newString(TYPE.fastGetName() + "(bits_per_digit=%r, sizeof_digit=%r)").__mod__(this);
   }
}
