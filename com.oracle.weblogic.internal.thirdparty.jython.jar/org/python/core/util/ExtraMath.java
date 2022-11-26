package org.python.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExtraMath {
   public static double EPSILON = Math.pow(2.0, -52.0);
   public static double CLOSE;

   public static boolean close(double v, double w, double tol) {
      if (v == w) {
         return true;
      } else {
         double scaled = tol * (Math.abs(v) + Math.abs(w)) / 2.0;
         return Math.abs(w - v) < scaled;
      }
   }

   public static boolean close(double v, double w) {
      return close(v, w, CLOSE);
   }

   public static double closeFloor(double v) {
      double floor = Math.floor(v);
      return close(v, floor + 1.0) ? floor + 1.0 : floor;
   }

   public static double round(double x, int n) {
      if (!Double.isNaN(x) && !Double.isInfinite(x) && x != 0.0) {
         float nlog2_10 = 3.3219F * (float)n;
         int b = Math.getExponent(x);
         if (nlog2_10 > (float)(52 - b)) {
            return x;
         } else if (nlog2_10 < (float)(-(b + 2))) {
            return Math.copySign(0.0, x);
         } else {
            BigDecimal xx = new BigDecimal(x);
            BigDecimal rr = xx.setScale(n, RoundingMode.HALF_UP);
            return rr.doubleValue();
         }
      } else {
         return x;
      }
   }

   static {
      CLOSE = EPSILON * 2.0;
   }
}
