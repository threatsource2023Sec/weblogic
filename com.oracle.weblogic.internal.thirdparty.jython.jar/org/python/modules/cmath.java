package org.python.modules;

import org.python.core.Py;
import org.python.core.PyComplex;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyInstance;
import org.python.core.PyObject;
import org.python.core.PyTuple;

public class cmath {
   public static final PyFloat pi = new PyFloat(Math.PI);
   public static final PyFloat e = new PyFloat(Math.E);
   private static final double ROOT_HALF = 0.7071067811865476;
   private static final double NEARLY_LN_DBL_MAX = 709.4361393;
   private static final double ATLEAST_27LN2 = 18.72;
   private static final double HALF_E2 = 3.6945280494653248;
   private static final double LOG10E = 0.4342944819032518;

   private static PyComplex complexFromPyObject(PyObject obj) {
      if (obj instanceof PyComplex) {
         return (PyComplex)obj;
      } else {
         PyObject newObj = null;
         if (obj instanceof PyInstance) {
            if (obj.__findattr__("__complex__") != null) {
               newObj = obj.invoke("__complex__");
            }
         } else {
            PyObject complexFunc = obj.getType().lookup("__complex__");
            if (complexFunc != null) {
               newObj = complexFunc.__call__(obj);
            }
         }

         if (newObj != null) {
            if (!(newObj instanceof PyComplex)) {
               throw Py.TypeError("__complex__ should return a complex object");
            } else {
               return (PyComplex)newObj;
            }
         } else {
            return new PyComplex(obj.asDouble(), 0.0);
         }
      }
   }

   public static PyComplex acos(PyObject w) {
      return _acos(complexFromPyObject(w));
   }

   private static PyComplex _acos(PyComplex w) {
      double u = w.real;
      double v = w.imag;
      double x;
      double y;
      if (!(Math.abs(u) > 1.34217728E8) && !(Math.abs(v) > 1.34217728E8)) {
         if (Double.isNaN(v)) {
            x = u == 0.0 ? 1.5707963267948966 : v;
            y = v;
         } else {
            PyComplex a = sqrt(new PyComplex(1.0 - u, -v));
            PyComplex b = sqrt(new PyComplex(1.0 + u, v));
            x = 2.0 * Math.atan2(a.real, b.real);
            y = math.asinh(a.imag * b.real - a.real * b.imag);
         }
      } else {
         x = Math.atan2(Math.abs(v), u);
         y = Math.copySign(logHypot(u, v) + 0.6931471805599453, -v);
      }

      return exceptNaN(new PyComplex(x, y), w);
   }

   public static PyComplex acosh(PyObject w) {
      return _acosh(complexFromPyObject(w));
   }

   private static PyComplex _acosh(PyComplex w) {
      double u = w.real;
      double v = w.imag;
      double x;
      double y;
      if (!(Math.abs(u) > 1.34217728E8) && !(Math.abs(v) > 1.34217728E8)) {
         if (v == 0.0 && !Double.isNaN(u)) {
            if (u >= 1.0) {
               x = math.acosh(u);
               y = v;
            } else if (u < -1.0) {
               x = math.acosh(-u);
               y = Math.copySign(Math.PI, v);
            } else {
               x = 0.0;
               y = Math.copySign(Math.acos(u), v);
            }
         } else {
            PyComplex a = sqrt(new PyComplex(u - 1.0, v));
            PyComplex b = sqrt(new PyComplex(u + 1.0, v));
            x = math.asinh(a.real * b.real + a.imag * b.imag);
            y = 2.0 * Math.atan2(a.imag, b.real);
         }
      } else {
         x = logHypot(u, v) + 0.6931471805599453;
         y = Math.atan2(v, u);
      }

      return exceptNaN(new PyComplex(x, y), w);
   }

   public static PyComplex asin(PyObject w) {
      return asinOrAsinh(complexFromPyObject(w), false);
   }

   public static PyComplex asinh(PyObject w) {
      return asinOrAsinh(complexFromPyObject(w), true);
   }

   private static PyComplex asinOrAsinh(PyComplex w, boolean h) {
      double u;
      double v;
      if (h) {
         u = w.real;
         v = w.imag;
      } else {
         v = w.real;
         u = -w.imag;
      }

      double x;
      double y;
      if (Double.isNaN(u)) {
         if (v == 0.0) {
            x = u;
            y = v;
         } else if (Double.isInfinite(v)) {
            x = Double.POSITIVE_INFINITY;
            y = u;
         } else {
            y = u;
            x = u;
         }
      } else if (!(Math.abs(u) > 1.34217728E8) && !(Math.abs(v) > 1.34217728E8)) {
         PyComplex a = sqrt(new PyComplex(1.0 + v, -u));
         PyComplex b = sqrt(new PyComplex(1.0 - v, u));
         x = math.asinh(a.real * b.imag - a.imag * b.real);
         y = Math.atan2(v, a.real * b.real - a.imag * b.imag);
      } else {
         x = logHypot(u, v) + 0.6931471805599453;
         if (Math.copySign(1.0, u) > 0.0) {
            y = Math.atan2(v, u);
         } else {
            x = -x;
            y = Math.atan2(v, -u);
         }
      }

      PyComplex z;
      if (h) {
         z = new PyComplex(x, y);
      } else {
         z = new PyComplex(y, -x);
      }

      return exceptNaN(z, w);
   }

   public static PyComplex atan(PyObject w) {
      return atanOrAtanh(complexFromPyObject(w), false);
   }

   public static PyComplex atanh(PyObject w) {
      return atanOrAtanh(complexFromPyObject(w), true);
   }

   private static PyComplex atanOrAtanh(PyComplex w, boolean h) {
      double u;
      double v;
      if (h) {
         u = w.real;
         v = w.imag;
      } else {
         v = w.real;
         u = -w.imag;
      }

      double absu = Math.abs(u);
      double absv = Math.abs(v);
      double x;
      double y;
      if (!(absu >= 6.7039039649712985E153) && !(absv >= 6.7039039649712985E153)) {
         double v2;
         double d;
         if (absu < 1.1102230246251565E-16) {
            if (absv > 7.450580596923828E-9) {
               v2 = v * v;
               d = 1.0 + v2;
               x = Math.copySign(Math.log1p(4.0 * absu / d), u) * 0.25;
               y = Math.atan2(2.0 * v, 1.0 - v2) * 0.5;
            } else {
               x = u;
               y = v;
            }
         } else if (absu == 1.0 && absv < 7.450580596923828E-9) {
            x = Math.copySign(Math.log(absv) - 0.6931471805599453, u) * 0.5;
            if (v == 0.0) {
               y = Double.NaN;
            } else {
               y = Math.copySign(Math.atan2(2.0, absv), v) * 0.5;
            }
         } else {
            v2 = 1.0 - absu;
            d = 1.0 + absu;
            double v2 = v * v;
            double d = v2 * v2 + v2;
            x = Math.copySign(Math.log1p(4.0 * absu / d), u) * 0.25;
            y = Math.atan2(2.0 * v, v2 * d - v2) * 0.5;
         }
      } else {
         if (!Double.isInfinite(absu) && !Double.isInfinite(absv)) {
            int N = 520;
            double uu = Math.scalb(u, -N);
            double vv = Math.scalb(v, -N);
            double mod2w = uu * uu + vv * vv;
            x = Math.scalb(uu / mod2w, -N);
         } else {
            x = Math.copySign(0.0, u);
         }

         if (Double.isNaN(v)) {
            y = v;
         } else {
            y = Math.copySign(1.5707963267948966, v);
         }
      }

      PyComplex z;
      if (h) {
         z = new PyComplex(x, y);
      } else {
         z = new PyComplex(y, -x);
      }

      return exceptNaN(z, w);
   }

   public static PyComplex cos(PyObject z) {
      return cosOrCosh(complexFromPyObject(z), false);
   }

   public static PyComplex cosh(PyObject z) {
      return cosOrCosh(complexFromPyObject(z), true);
   }

   private static PyComplex cosOrCosh(PyComplex z, boolean h) {
      double x;
      double y;
      if (h) {
         x = z.real;
         y = z.imag;
      } else {
         y = z.real;
         x = -z.imag;
      }

      double u;
      double v;
      if (y == 0.0) {
         u = math.cosh(x);
         v = Math.copySign(1.0, x) * y;
      } else if (x == 0.0) {
         u = Math.cos(y);
         v = x * Math.copySign(1.0, y);
      } else {
         double cosy = Math.cos(y);
         double siny = Math.sin(y);
         double absx = Math.abs(x);
         if (absx == Double.POSITIVE_INFINITY) {
            if (!Double.isNaN(cosy)) {
               u = absx * cosy;
               v = x * siny;
            } else {
               u = absx;
               v = Double.NaN;
            }
         } else if (absx > 18.72) {
            double r = Math.exp(absx - 2.0);
            u = r * cosy * 3.6945280494653248;
            v = Math.copySign(r, x) * siny * 3.6945280494653248;
            if (Double.isInfinite(u) || Double.isInfinite(v)) {
               throw math.mathRangeError();
            }
         } else {
            u = Math.cosh(x) * cosy;
            v = Math.sinh(x) * siny;
         }
      }

      PyComplex w = new PyComplex(u, v);
      return exceptNaN(w, z);
   }

   public static PyComplex exp(PyObject z) {
      PyComplex zz = complexFromPyObject(z);
      double x = zz.real;
      double y = zz.imag;
      double u;
      double v;
      if (y == 0.0) {
         u = math.exp(x);
         v = y;
      } else {
         double cosy = Math.cos(y);
         double siny = Math.sin(y);
         if (x == Double.NEGATIVE_INFINITY) {
            u = Math.copySign(0.0, cosy);
            v = Math.copySign(0.0, siny);
         } else if (x == Double.POSITIVE_INFINITY) {
            if (!Double.isNaN(cosy)) {
               u = Math.copySign(x, cosy);
               v = Math.copySign(x, siny);
            } else {
               u = x;
               v = Double.NaN;
            }
         } else {
            double r;
            if (x > 709.4361393) {
               r = Math.exp(x - 1.0);
               u = r * cosy * Math.E;
               v = r * siny * Math.E;
               if (Double.isInfinite(u) || Double.isInfinite(v)) {
                  throw math.mathRangeError();
               }
            } else {
               r = Math.exp(x);
               u = r * cosy;
               v = r * siny;
            }
         }
      }

      return exceptNaN(new PyComplex(u, v), zz);
   }

   public static double phase(PyObject in) {
      PyComplex x = complexFromPyObject(in);
      return Math.atan2(x.imag, x.real);
   }

   public static PyTuple polar(PyObject in) {
      PyComplex z = complexFromPyObject(in);
      double phi = Math.atan2(z.imag, z.real);
      double r = math.hypot(z.real, z.imag);
      return new PyTuple(new PyObject[]{new PyFloat(r), new PyFloat(phi)});
   }

   public static PyComplex rect(double r, double phi) {
      double x;
      double y;
      if (!Double.isInfinite(r) || !Double.isInfinite(phi) && !Double.isNaN(phi)) {
         if (phi == 0.0) {
            x = r;
            if (Double.isNaN(r)) {
               y = phi;
            } else if (Double.isInfinite(r)) {
               y = phi * Math.copySign(1.0, r);
            } else {
               y = phi * r;
            }
         } else if (r != 0.0 || !Double.isInfinite(phi) && !Double.isNaN(phi)) {
            x = r * Math.cos(phi);
            y = r * Math.sin(phi);
         } else {
            y = 0.0;
            x = 0.0;
         }
      } else {
         x = Double.POSITIVE_INFINITY;
         y = Double.NaN;
      }

      return exceptNaN(new PyComplex(x, y), r, phi);
   }

   public static boolean isinf(PyObject in) {
      PyComplex x = complexFromPyObject(in);
      return Double.isInfinite(x.real) || Double.isInfinite(x.imag);
   }

   public static boolean isnan(PyObject in) {
      PyComplex x = complexFromPyObject(in);
      return Double.isNaN(x.real) || Double.isNaN(x.imag);
   }

   public static PyComplex log(PyObject w) {
      PyComplex ww = complexFromPyObject(w);
      double u = ww.real;
      double v = ww.imag;
      double lnr = logHypot(u, v);
      double theta = Math.atan2(v, u);
      PyComplex z = new PyComplex(lnr, theta);
      return exceptNaN(z, ww);
   }

   public static PyComplex log10(PyObject w) {
      PyComplex ww = complexFromPyObject(w);
      double u = ww.real;
      double v = ww.imag;
      double logr = logHypot(u, v) * 0.4342944819032518;
      double theta = Math.atan2(v, u) * 0.4342944819032518;
      PyComplex z = new PyComplex(logr, theta);
      return exceptNaN(z, ww);
   }

   public static PyComplex log(PyObject w, PyObject b) {
      PyComplex ww = complexFromPyObject(w);
      PyComplex bb = complexFromPyObject(b);
      double u = ww.real;
      double v = ww.imag;
      double br = bb.real;
      double bi = bb.imag;
      double x = logHypot(u, v);
      double y = Math.atan2(v, u);
      PyComplex z;
      if (bi == 0.0 && !(br <= 0.0)) {
         double lnb = Math.log(br);
         z = new PyComplex(x / lnb, y / lnb);
      } else {
         PyComplex lnb = log(bb);
         z = (PyComplex)(new PyComplex(x, y)).__div__(lnb);
      }

      return exceptNaN(z, ww);
   }

   private static double logHypot(double u, double v) {
      if (!Double.isInfinite(u) && !Double.isInfinite(v)) {
         int scale = 0;
         int ue = Math.getExponent(u);
         int ve = Math.getExponent(v);
         if (ue < -511 && ve < -511) {
            scale = 600;
         } else if (ue > 510 || ve > 510) {
            scale = -600;
         }

         double lnr;
         if (scale == 0) {
            lnr = 0.5 * Math.log(u * u + v * v);
         } else {
            double us = Math.scalb(u, scale);
            double vs = Math.scalb(v, scale);
            double rs2 = us * us + vs * vs;
            lnr = 0.5 * Math.log(rs2) - (double)scale * 0.6931471805599453;
         }

         if (lnr == Double.NEGATIVE_INFINITY) {
            throw math.mathDomainError();
         } else {
            return lnr;
         }
      } else {
         return Double.POSITIVE_INFINITY;
      }
   }

   public static PyComplex sin(PyObject z) {
      return sinOrSinh(complexFromPyObject(z), false);
   }

   public static PyComplex sinh(PyObject z) {
      return sinOrSinh(complexFromPyObject(z), true);
   }

   private static PyComplex sinOrSinh(PyComplex z, boolean h) {
      double x;
      double y;
      if (h) {
         x = z.real;
         y = z.imag;
      } else {
         y = z.real;
         x = -z.imag;
      }

      double u;
      double v;
      if (y == 0.0) {
         u = math.sinh(x);
         v = y;
      } else if (x == 0.0) {
         v = Math.sin(y);
         u = x;
      } else {
         double cosy = Math.cos(y);
         double siny = Math.sin(y);
         double absx = Math.abs(x);
         if (absx == Double.POSITIVE_INFINITY) {
            if (!Double.isNaN(cosy)) {
               u = x * cosy;
               v = absx * siny;
            } else {
               u = x;
               v = Double.NaN;
            }
         } else if (absx > 18.72) {
            double r = Math.exp(absx - 2.0);
            v = r * siny * 3.6945280494653248;
            u = Math.copySign(r, x) * cosy * 3.6945280494653248;
            if (Double.isInfinite(u) || Double.isInfinite(v)) {
               throw math.mathRangeError();
            }
         } else {
            u = Math.sinh(x) * cosy;
            v = Math.cosh(x) * siny;
         }
      }

      PyComplex w;
      if (h) {
         w = new PyComplex(u, v);
      } else {
         w = new PyComplex(v, -u);
      }

      return exceptNaN(w, z);
   }

   public static PyComplex sqrt(PyObject w) {
      PyComplex ww = complexFromPyObject(w);
      double u = Math.abs(ww.real);
      double v = Math.abs(ww.imag);
      double x;
      double y;
      if (Double.isInfinite(u)) {
         x = Double.POSITIVE_INFINITY;
         y = !Double.isNaN(v) && !Double.isInfinite(v) ? 0.0 : v;
      } else if (Double.isInfinite(v)) {
         y = Double.POSITIVE_INFINITY;
         x = Double.POSITIVE_INFINITY;
      } else if (Double.isNaN(u)) {
         y = u;
         x = u;
      } else if (v == 0.0) {
         x = u == 0.0 ? 0.0 : Math.sqrt(u);
         y = 0.0;
      } else if (u == 0.0) {
         x = y = 0.7071067811865476 * Math.sqrt(v);
      } else {
         int ue = Math.getExponent(u);
         int ve = Math.getExponent(v);
         int diff = ue - ve;
         if (diff > 27) {
            x = Math.sqrt(u);
         } else if (diff < -27) {
            if (ve >= 1023) {
               x = Math.sqrt(0.5 * u + 0.5 * v);
            } else {
               x = Math.sqrt(0.5 * (u + v));
            }
         } else {
            int LARGE = true;
            int SMALL = true;
            int SCALE = true;
            int n = 0;
            double a;
            double b;
            if (ue <= 510 && ve <= 510) {
               if (ue < -510 && ve < -510) {
                  a = Math.scalb(u, 599);
                  b = Math.scalb(v, 599);
                  n = 600;
               } else {
                  a = 0.5 * u;
                  b = 0.5 * v;
               }
            } else {
               a = Math.scalb(u, -601);
               b = Math.scalb(v, -601);
               n = -600;
            }

            double s = Math.sqrt(a * a + b * b);
            x = Math.sqrt(s + a);
            if (n != 0) {
               x = Math.scalb(x, -n / 2);
            }
         }

         y = v / (x + x);
      }

      return ww.real < 0.0 ? new PyComplex(y, Math.copySign(x, ww.imag)) : new PyComplex(x, Math.copySign(y, ww.imag));
   }

   public static PyComplex tan(PyObject z) {
      return tanOrTanh(complexFromPyObject(z), false);
   }

   public static PyComplex tanh(PyObject z) {
      return tanOrTanh(complexFromPyObject(z), true);
   }

   private static PyComplex tanOrTanh(PyComplex z, boolean h) {
      double x;
      double y;
      if (h) {
         x = z.real;
         y = z.imag;
      } else {
         y = z.real;
         x = -z.imag;
      }

      double u;
      double v;
      if (y == 0.0) {
         u = Math.tanh(x);
         v = y;
      } else if (x == 0.0 && !Double.isNaN(y)) {
         v = Math.tan(y);
         u = x;
      } else {
         double cosy = Math.cos(y);
         double siny = Math.sin(y);
         double absx = Math.abs(x);
         double s;
         if (absx > 18.72) {
            s = 0.25 * Math.exp(2.0 * absx);
            u = Math.copySign(1.0, x);
            if (s == Double.POSITIVE_INFINITY) {
               v = Math.copySign(0.0, siny * cosy);
            } else {
               v = siny * cosy / s;
            }
         } else {
            double sinhx = Math.sinh(x);
            double coshx = Math.cosh(x);
            s = sinhx * sinhx + cosy * cosy;
            u = sinhx * coshx / s;
            v = siny * cosy / s;
         }
      }

      PyComplex w;
      if (h) {
         w = new PyComplex(u, v);
      } else {
         w = new PyComplex(v, -u);
      }

      return exceptNaN(w, z);
   }

   private static PyComplex exceptNaN(PyComplex result, PyComplex arg) throws PyException {
      if ((Double.isNaN(result.real) || Double.isNaN(result.imag)) && !Double.isNaN(arg.real) && !Double.isNaN(arg.imag)) {
         throw math.mathDomainError();
      } else {
         return result;
      }
   }

   private static PyComplex exceptNaN(PyComplex result, double a, double b) throws PyException {
      if ((Double.isNaN(result.real) || Double.isNaN(result.imag)) && !Double.isNaN(a) && !Double.isNaN(b)) {
         throw math.mathDomainError();
      } else {
         return result;
      }
   }
}
