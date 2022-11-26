package org.python.core;

import org.python.core.util.ExtraMath;

@Untraversable
class RoundFunction extends PyBuiltinFunction {
   RoundFunction() {
      super("round", "round(number[, ndigits]) -> floating point number\n\nRound a number to a given precision in decimal digits (default 0 digits).\nThis always returns a floating point number.  Precision may be negative.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("round", args, kwds, new String[]{"number", "ndigits"}, 0);
      PyObject number = ap.getPyObject(0);
      int ndigits = ap.getIndex(1, 0);
      double x = number.asDouble();
      double r = ExtraMath.round(x, ndigits);
      if (Double.isInfinite(r) && !Double.isInfinite(x)) {
         throw Py.OverflowError("rounded value too large to represent");
      } else {
         return new PyFloat(r);
      }
   }
}
