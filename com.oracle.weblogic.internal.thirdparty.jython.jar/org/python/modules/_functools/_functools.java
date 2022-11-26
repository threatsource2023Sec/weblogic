package org.python.modules._functools;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

public class _functools implements ClassDictInit {
   public static final PyString __doc__ = new PyString("Tools that operate on functions.");
   public static PyString __doc__reduce = new PyString("reduce(function, sequence[, initial]) -> value\n\nApply a function of two arguments cumulatively to the items of a sequence,\nfrom left to right, so as to reduce the sequence to a single value.\nFor example, reduce(lambda x, y: x+y, [1, 2, 3, 4, 5]) calculates\n((((1+2)+3)+4)+5).  If initial is present, it is placed before the items\nof the sequence in the calculation, and serves as a default when the\nsequence is empty.");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("_functools"));
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"partial", PyPartial.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   public static PyObject reduce(PyObject f, PyObject l, PyObject z) {
      PyObject result = z;
      PyObject iter = Py.iter(l, "reduce() arg 2 must support iteration");

      PyObject item;
      while((item = iter.__iternext__()) != null) {
         if (result == null) {
            result = item;
         } else {
            result = f.__call__(result, item);
         }
      }

      if (result == null) {
         throw Py.TypeError("reduce of empty sequence with no initial value");
      } else {
         return result;
      }
   }

   public static PyObject reduce(PyObject f, PyObject l) {
      return reduce(f, l, (PyObject)null);
   }
}
