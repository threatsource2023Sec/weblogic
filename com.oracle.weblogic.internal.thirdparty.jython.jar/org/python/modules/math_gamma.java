package org.python.modules;

import org.python.core.Py;

public class math_gamma {
   static final double pi = Math.PI;
   static final double sqrtpi = 1.772453850905516;
   static final int LANCZOS_N = 13;
   static final double lanczos_g = 6.02468004077673;
   static final double lanczos_g_minus_half = 5.52468004077673;
   static final double[] lanczos_num_coeffs = new double[]{2.353137688041076E10, 4.29198036426491E10, 3.571195923735567E10, 1.792103442603721E10, 6.039542586352028E9, 1.4397204073117216E9, 2.4887455786205417E8, 3.1426415585400194E7, 2876370.6289353725, 186056.26539522348, 8071.672002365816, 210.82427775157936, 2.5066282746310002};
   static final double[] lanczos_den_coeffs = new double[]{0.0, 3.99168E7, 1.2054384E8, 1.50917976E8, 1.05258076E8, 4.599573E7, 1.3339535E7, 2637558.0, 357423.0, 32670.0, 1925.0, 66.0, 1.0};
   static final int NGAMMA_INTEGRAL = 23;
   static final double[] gamma_integral = new double[]{1.0, 1.0, 2.0, 6.0, 24.0, 120.0, 720.0, 5040.0, 40320.0, 362880.0, 3628800.0, 3.99168E7, 4.790016E8, 6.2270208E9, 8.71782912E10, 1.307674368E12, 2.0922789888E13, 3.55687428096E14, 6.402373705728E15, 1.21645100408832E17, 2.43290200817664E18, 5.109094217170944E19, 1.1240007277776077E21};

   static double sinpi(double x) {
      double y = Math.abs(x) % 2.0;
      int n = (int)Math.round(2.0 * y);

      assert 0 <= n && n <= 4;

      double r;
      switch (n) {
         case 0:
            r = Math.sin(Math.PI * y);
            break;
         case 1:
            r = Math.cos(Math.PI * (y - 0.5));
            break;
         case 2:
            r = Math.sin(Math.PI * (1.0 - y));
            break;
         case 3:
            r = -Math.cos(Math.PI * (y - 1.5));
            break;
         case 4:
            r = Math.sin(Math.PI * (y - 2.0));
            break;
         default:
            assert false;

            r = 3.0;
      }

      return Math.copySign(1.0, x) * r;
   }

   static double lanczos_sum(double x) {
      double num = 0.0;
      double den = 0.0;

      assert x > 0.0;

      int i;
      if (x < 5.0) {
         i = 13;

         while(true) {
            --i;
            if (i < 0) {
               break;
            }

            num = num * x + lanczos_num_coeffs[i];
            den = den * x + lanczos_den_coeffs[i];
         }
      } else {
         for(i = 0; i < 13; ++i) {
            num = num / x + lanczos_num_coeffs[i];
            den = den / x + lanczos_den_coeffs[i];
         }
      }

      return num / den;
   }

   public static double gamma(double x) {
      if (Double.isNaN(x)) {
         return x;
      } else if (Double.isInfinite(x)) {
         if (x > 0.0) {
            return x;
         } else {
            throw Py.ValueError("math domain error");
         }
      } else if (x == 0.0) {
         throw Py.ValueError("math domain error");
      } else {
         if (x == Math.floor(x)) {
            if (x < 0.0) {
               throw Py.ValueError("math domain error");
            }

            if (x <= 23.0) {
               return gamma_integral[(int)x - 1];
            }
         }

         double absx = Math.abs(x);
         double r;
         if (absx < 1.0E-20) {
            r = 1.0 / x;
            if (Double.isInfinite(r)) {
               throw Py.OverflowError("math range error");
            } else {
               return r;
            }
         } else if (absx > 200.0) {
            if (x < 0.0) {
               return 0.0 / sinpi(x);
            } else {
               throw Py.OverflowError("math range error");
            }
         } else {
            double y = absx + 5.52468004077673;
            double q;
            double z;
            if (absx > 5.52468004077673) {
               q = y - absx;
               z = q - 5.52468004077673;
            } else {
               q = y - 5.52468004077673;
               z = q - absx;
            }

            z = z * 6.02468004077673 / y;
            double sqrtpow;
            if (x < 0.0) {
               r = -3.141592653589793 / sinpi(absx) / absx * Math.exp(y) / lanczos_sum(absx);
               r -= z * r;
               if (absx < 140.0) {
                  r /= Math.pow(y, absx - 0.5);
               } else {
                  sqrtpow = Math.pow(y, absx / 2.0 - 0.25);
                  r /= sqrtpow;
                  r /= sqrtpow;
               }
            } else {
               r = lanczos_sum(absx) / Math.exp(y);
               r += z * r;
               if (absx < 140.0) {
                  r *= Math.pow(y, absx - 0.5);
               } else {
                  sqrtpow = Math.pow(y, absx / 2.0 - 0.25);
                  r *= sqrtpow;
                  r *= sqrtpow;
               }
            }

            if (Double.isInfinite(r)) {
               throw Py.OverflowError("math range error");
            } else {
               return r;
            }
         }
      }
   }

   public static double lgamma(double x) {
      if (Double.isNaN(x)) {
         return x;
      } else if (Double.isInfinite(x)) {
         return Double.POSITIVE_INFINITY;
      } else if (x == Math.floor(x) && x <= 2.0) {
         if (x <= 0.0) {
            throw Py.ValueError("math domain error");
         } else {
            return 0.0;
         }
      } else {
         double absx = Math.abs(x);
         if (absx < 1.0E-20) {
            return -Math.log(absx);
         } else {
            double r;
            if (x > 0.0) {
               r = Math.log(lanczos_sum(x)) - 6.02468004077673 + (x - 0.5) * (Math.log(x + 6.02468004077673 - 0.5) - 1.0);
            } else {
               r = Math.log(Math.PI) - Math.log(Math.abs(sinpi(absx))) - Math.log(absx) - (Math.log(lanczos_sum(absx)) - 6.02468004077673 + (absx - 0.5) * (Math.log(absx + 6.02468004077673 - 0.5) - 1.0));
            }

            if (Double.isInfinite(r)) {
               throw Py.OverflowError("math range error");
            } else {
               return r;
            }
         }
      }
   }
}
