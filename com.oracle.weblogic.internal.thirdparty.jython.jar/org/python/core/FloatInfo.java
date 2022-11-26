package org.python.core;

import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "sys.float_info",
   isBaseType = false
)
class FloatInfo extends PyTuple {
   @ExposedGet
   public PyObject max;
   @ExposedGet
   public PyObject max_exp;
   @ExposedGet
   public PyObject max_10_exp;
   @ExposedGet
   public PyObject min;
   @ExposedGet
   public PyObject min_exp;
   @ExposedGet
   public PyObject min_10_exp;
   @ExposedGet
   public PyObject dig;
   @ExposedGet
   public PyObject mant_dig;
   @ExposedGet
   public PyObject epsilon;
   @ExposedGet
   public PyObject radix;
   @ExposedGet
   public PyObject rounds;
   public static final PyType TYPE = PyType.fromClass(FloatInfo.class);

   private FloatInfo(PyObject... vals) {
      super(TYPE, vals);
      this.max = vals[0];
      this.max_exp = vals[1];
      this.max_10_exp = vals[2];
      this.min = vals[3];
      this.min_exp = vals[4];
      this.min_10_exp = vals[5];
      this.dig = vals[6];
      this.mant_dig = vals[7];
      this.epsilon = vals[8];
      this.radix = vals[9];
      this.rounds = vals[10];
   }

   public static FloatInfo getInfo() {
      return new FloatInfo(new PyObject[]{Py.newFloat(Double.MAX_VALUE), Py.newLong(1023), Py.newLong(308), Py.newFloat(Double.MIN_VALUE), Py.newLong(-1022), Py.newLong(-307), Py.newLong(10), Py.newLong(53), Py.newFloat(2.220446049250313E-16), Py.newLong(2), Py.newLong(1)});
   }

   public PyString __repr__() {
      return (PyString)Py.newString(TYPE.fastGetName() + "(max=%r, max_exp=%r, max_10_exp=%r, min=%r, min_exp=%r, min_10_exp=%r, dig=%r, mant_dig=%r, epsilon=%r, radix=%r, rounds=%r)").__mod__(this);
   }
}
