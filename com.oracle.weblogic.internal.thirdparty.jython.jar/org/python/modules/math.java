package org.python.modules;

import java.math.BigInteger;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.__builtin__;

public class math implements ClassDictInit {
   public static PyFloat pi = new PyFloat(Math.PI);
   public static PyFloat e = new PyFloat(Math.E);
   private static final double ZERO = 0.0;
   private static final double MINUS_ZERO = -0.0;
   private static final double ONE = 1.0;
   private static final double MINUS_ONE = -1.0;
   private static final double TWO = 2.0;
   private static final double EIGHT = 8.0;
   static final double LN2 = 0.6931471805599453;
   private static final double INF = Double.POSITIVE_INFINITY;
   private static final double NINF = Double.NEGATIVE_INFINITY;
   private static final double NAN = Double.NaN;
   private static final BigInteger MAX_LONG_BIGINTEGER = new BigInteger(String.valueOf(Long.MAX_VALUE));
   private static final BigInteger MIN_LONG_BIGINTEGER = new BigInteger(String.valueOf(Long.MIN_VALUE));

   public static void classDictInit(PyObject dict) {
   }

   public static double gamma(double v) {
      return math_gamma.gamma(v);
   }

   public static double lgamma(double v) {
      return math_gamma.lgamma(v);
   }

   public static double erf(double v) {
      return math_erf.erf(v);
   }

   public static double erfc(double v) {
      return math_erf.erfc(v);
   }

   public static double expm1(double v) {
      if (Double.POSITIVE_INFINITY == v) {
         return v;
      } else {
         double result = Math.expm1(v);
         if (Double.isInfinite(result)) {
            throw Py.OverflowError(Double.toString(v));
         } else {
            return result;
         }
      }
   }

   public static double acos(double v) {
      return exceptNaN(Math.acos(v), v);
   }

   public static double acosh(double y) {
      if (y < 1.0) {
         throw mathDomainError();
      } else {
         double u;
         double t;
         if (y < 2.0) {
            u = y - 1.0;
            t = Math.sqrt(u * (2.0 + u));
            return Math.log1p(u + t);
         } else if (y < 1.34217728E8) {
            u = 1.0 / y;
            t = Math.sqrt((1.0 + u) * (1.0 - u));
            return Math.log(y * (1.0 + t));
         } else {
            return Math.log(y) + 0.6931471805599453;
         }
      }
   }

   public static double asin(double v) {
      return exceptNaN(Math.asin(v), v);
   }

   public static double asinh(double v) {
      if (!isnan(v) && !isinf(v)) {
         double ln2 = 0.6931471805599453;
         double large = 2.68435456E8;
         double small = 3.725290298461914E-9;
         boolean sign = false;
         if (v < 0.0) {
            v = -v;
            sign = true;
         }

         double temp;
         if (v > 2.68435456E8) {
            temp = log(v) + 0.6931471805599453;
         } else if (v > 2.0) {
            temp = log(2.0 * v + 1.0 / (sqrt(v * v + 1.0) + v));
         } else if (v < 3.725290298461914E-9) {
            temp = v;
         } else {
            temp = log1p(v + v * v / (1.0 + sqrt(1.0 + v * v)));
         }

         return sign ? -temp : temp;
      } else {
         return v;
      }
   }

   public static double atan(double v) {
      return exceptNaN(Math.atan(v), v);
   }

   public static double atanh(double y) {
      double absy = Math.abs(y);
      if (absy >= 1.0) {
         throw mathDomainError();
      } else {
         double u = (absy + absy) / (1.0 - absy);
         double x = 0.5 * Math.log1p(u);
         return Math.copySign(x, y);
      }
   }

   public static double atan2(double v, double w) {
      return Math.atan2(v, w);
   }

   public static double ceil(PyObject v) {
      return ceil(v.asDouble());
   }

   public static double ceil(double v) {
      return Math.ceil(v);
   }

   public static double cos(double v) {
      return exceptNaN(Math.cos(v), v);
   }

   public static double cosh(double v) {
      return exceptInf(Math.cosh(v), v);
   }

   public static double exp(double v) {
      return exceptInf(Math.exp(v), v);
   }

   public static double floor(PyObject v) {
      return floor(v.asDouble());
   }

   public static double floor(double v) {
      return Math.floor(v);
   }

   public static double log(PyObject v) {
      return log(v, (PyObject)null);
   }

   public static double log(PyObject v, PyObject base) {
      double doubleValue;
      if (v instanceof PyLong) {
         doubleValue = calculateLongLog((PyLong)v);
      } else {
         doubleValue = log(v.asDouble());
      }

      return base == null ? doubleValue : applyLoggedBase(doubleValue, base);
   }

   public static double pow(double v, double w) {
      if (w == 0.0) {
         return 1.0;
      } else if (v == 1.0) {
         return v;
      } else if (!isnan(v) && !isnan(w)) {
         if (v == 0.0) {
            if (w == 0.0) {
               return 1.0;
            } else if (!(w > 0.0) && !ispinf(w)) {
               throw mathDomainError();
            } else {
               return 0.0;
            }
         } else if (isninf(v)) {
            if (isninf(w)) {
               return 0.0;
            } else if (isinf(w)) {
               return Double.POSITIVE_INFINITY;
            } else if (w == 0.0) {
               return 1.0;
            } else if (w > 0.0) {
               return isOdd(w) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else {
               return isOdd(w) ? -0.0 : 0.0;
            }
         } else if (isninf(w) && v < 0.0) {
            if (v == -1.0) {
               return 1.0;
            } else {
               return v < -1.0 ? 0.0 : Double.POSITIVE_INFINITY;
            }
         } else if (ispinf(w) && v < 0.0) {
            if (v == -1.0) {
               return 1.0;
            } else {
               return v < -1.0 ? Double.POSITIVE_INFINITY : 0.0;
            }
         } else if (v < 0.0 && !isIntegral(w)) {
            throw mathDomainError();
         } else {
            return Math.pow(v, w);
         }
      } else {
         return Double.NaN;
      }
   }

   public static double sin(PyObject v) {
      return sin(v.asDouble());
   }

   public static double sin(double v) {
      return exceptNaN(Math.sin(v), v);
   }

   public static double sqrt(PyObject v) {
      return sqrt(v.asDouble());
   }

   public static double sqrt(double v) {
      return exceptNaN(Math.sqrt(v), v);
   }

   public static double tan(double v) {
      return exceptNaN(Math.tan(v), v);
   }

   public static double log10(PyObject v) {
      if (v instanceof PyLong) {
         int[] exp = new int[1];
         double x = ((PyLong)v).scaledDoubleValue(exp);
         if (x <= 0.0) {
            throw mathDomainError();
         } else {
            return log10(x) + (double)exp[0] * 8.0 * log10(2.0);
         }
      } else {
         return log10(v.asDouble());
      }
   }

   public static double sinh(double v) {
      return exceptInf(Math.sinh(v), v);
   }

   public static double tanh(double v) {
      return exceptInf(Math.tanh(v), v);
   }

   public static double fabs(double v) {
      return Math.abs(v);
   }

   public static double fmod(double v, double w) {
      if (!isnan(v) && !isnan(w)) {
         if (isinf(w)) {
            return v;
         } else if (w == 0.0) {
            throw mathDomainError();
         } else if (isinf(v) && w == 1.0) {
            throw mathDomainError();
         } else {
            return v % w;
         }
      } else {
         return Double.NaN;
      }
   }

   public static PyTuple modf(double v) {
      if (isnan(v)) {
         return new PyTuple(new PyObject[]{new PyFloat(v), new PyFloat(v)});
      } else {
         double first;
         if (isinf(v)) {
            first = 0.0;
            if (isninf(v)) {
               first = -0.0;
            }

            return new PyTuple(new PyObject[]{new PyFloat(first), new PyFloat(v)});
         } else {
            first = v % 1.0;
            v -= first;
            return new PyTuple(new PyObject[]{new PyFloat(first), new PyFloat(v)});
         }
      }
   }

   public static PyTuple frexp(double x) {
      int exponent;
      double mantissa;
      switch (exponent = Math.getExponent(x)) {
         case -1023:
            if (x == 0.0) {
               mantissa = x;
               exponent = 0;
            } else {
               exponent = Math.getExponent(x * 4.503599627370496E15) - 51;
               mantissa = Math.scalb(x, -exponent);
            }
            break;
         case 1024:
            mantissa = x;
            exponent = 0;
            break;
         default:
            ++exponent;
            mantissa = Math.scalb(x, -exponent);
      }

      return new PyTuple(new PyObject[]{new PyFloat(mantissa), new PyInteger(exponent)});
   }

   public static PyObject trunc(PyObject number) {
      return number.__getattr__("__trunc__").__call__();
   }

   public static double ldexp(double v, PyObject wObj) {
      long w = getLong(wObj);
      if (w < -2147483648L) {
         w = -2147483648L;
      } else if (w > 2147483647L) {
         w = 2147483647L;
      }

      return exceptInf(Math.scalb(v, (int)w), v);
   }

   public static double hypot(double x, double y) {
      double mag = Math.hypot(x, y);
      if (Double.isInfinite(mag) && !Double.isInfinite(x) && !Double.isInfinite(y)) {
         throw mathRangeError();
      } else {
         return mag;
      }
   }

   public static double radians(double v) {
      return Math.toRadians(v);
   }

   public static double degrees(double v) {
      return Math.toDegrees(v);
   }

   public static boolean isnan(double v) {
      return Double.isNaN(v);
   }

   public static boolean isinf(double v) {
      return Double.isInfinite(v);
   }

   public static double copysign(double v, double w) {
      return Math.copySign(v, w);
   }

   public static PyLong factorial(double v) {
      if (v != 0.0 && v != 1.0) {
         if (!(v < 0.0) && !isnan(v) && !isinf(v)) {
            if (!isIntegral(v)) {
               throw mathDomainError();
            } else {
               long value = (long)v;
               BigInteger bi = new BigInteger(Long.toString(value));

               for(long l = value - 1L; l > 1L; --l) {
                  bi = bi.multiply(new BigInteger(Long.toString(l)));
               }

               return new PyLong(bi);
            }
         } else {
            throw mathDomainError();
         }
      } else {
         return new PyLong(1L);
      }
   }

   public static double log1p(double v) {
      if (v <= -1.0) {
         throw mathDomainError();
      } else {
         return Math.log1p(v);
      }
   }

   public static double fsum(PyObject iterable) {
      PyFloat result = (PyFloat)__builtin__.__import__("_fsum").invoke("fsum", iterable);
      return result.asDouble();
   }

   private static double calculateLongLog(PyLong v) {
      int[] exp = new int[1];
      double x = v.scaledDoubleValue(exp);
      if (x <= 0.0) {
         throw mathDomainError();
      } else {
         return log(x) + (double)exp[0] * 8.0 * log(2.0);
      }
   }

   private static double applyLoggedBase(double loggedValue, PyObject base) {
      double loggedBase;
      if (base instanceof PyLong) {
         loggedBase = calculateLongLog((PyLong)base);
      } else {
         loggedBase = log(base.asDouble());
      }

      return loggedValue / loggedBase;
   }

   private static double log(double v) {
      if (v <= 0.0) {
         throw mathDomainError();
      } else {
         return Math.log(v);
      }
   }

   private static double log10(double v) {
      if (v <= 0.0) {
         throw mathDomainError();
      } else {
         return Math.log10(v);
      }
   }

   private static boolean isninf(double v) {
      return v == Double.NEGATIVE_INFINITY;
   }

   private static boolean ispinf(double v) {
      return v == Double.POSITIVE_INFINITY;
   }

   static PyException mathDomainError() {
      return Py.ValueError("math domain error");
   }

   static PyException mathRangeError() {
      return Py.OverflowError("math range error");
   }

   private static double exceptNaN(double result, double arg) throws PyException {
      if (Double.isNaN(result) && !Double.isNaN(arg)) {
         throw mathDomainError();
      } else {
         return result;
      }
   }

   private static double exceptInf(double result, double arg) {
      if (Double.isInfinite(result) && !Double.isInfinite(arg)) {
         throw mathRangeError();
      } else {
         return result;
      }
   }

   private static long getLong(PyObject pyo) {
      return pyo instanceof PyLong ? getLong((PyLong)pyo) : pyo.asLong();
   }

   private static long getLong(PyLong pyLong) {
      BigInteger value = pyLong.getValue();
      if (value.compareTo(MAX_LONG_BIGINTEGER) > 0) {
         return Long.MAX_VALUE;
      } else {
         return value.compareTo(MIN_LONG_BIGINTEGER) < 0 ? Long.MIN_VALUE : value.longValue();
      }
   }

   private static boolean isIntegral(double v) {
      return ceil(v) - v == 0.0;
   }

   private static boolean isOdd(double v) {
      return isIntegral(v) && v % 2.0 != 0.0;
   }
}
