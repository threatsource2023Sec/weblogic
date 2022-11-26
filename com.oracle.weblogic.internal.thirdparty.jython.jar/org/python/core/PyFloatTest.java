package org.python.core;

import junit.framework.TestCase;

public class PyFloatTest extends TestCase {
   private static final double nan = Double.NaN;
   private static final double inf = Double.POSITIVE_INFINITY;
   private static final double ninf = Double.NEGATIVE_INFINITY;

   public void test_Double_InfinityAndNaN() {
      assertTrue(true);
      assertTrue(Double.isNaN(Double.NaN));
      assertFalse(Double.isInfinite(Double.NaN));
      assertTrue(true);
      assertFalse(Double.isNaN(Double.POSITIVE_INFINITY));
      assertTrue(Double.isInfinite(Double.POSITIVE_INFINITY));
      assertTrue(true);
      assertFalse(Double.isNaN(Double.NEGATIVE_INFINITY));
      assertTrue(Double.isInfinite(Double.NEGATIVE_INFINITY));
      assertTrue(true);
      assertTrue(true);
      assertTrue(true);
   }

   public void testInfinityAndNaN() {
      PyFloat fNan = new PyFloat(Double.NaN);
      PyFloat fInf = new PyFloat(Double.POSITIVE_INFINITY);
      PyFloat fNinf = new PyFloat(Double.NEGATIVE_INFINITY);
      assertTrue(Double.NaN != fNan.getValue());
      assertTrue(Double.isNaN(fNan.getValue()));
      assertFalse(Double.isInfinite(fNan.getValue()));
      assertTrue(Double.POSITIVE_INFINITY == fInf.getValue());
      assertFalse(Double.isNaN(fInf.getValue()));
      assertTrue(Double.isInfinite(fInf.getValue()));
      assertTrue(Double.NEGATIVE_INFINITY == fNinf.getValue());
      assertFalse(Double.isNaN(fNinf.getValue()));
      assertTrue(Double.isInfinite(fNinf.getValue()));
      assertTrue(fNan.getValue() != fInf.getValue());
      assertTrue(fNan.getValue() != fNinf.getValue());
      assertTrue(fInf.getValue() != fNinf.getValue());
   }

   public void testInfinityAndNaN_repr() {
      PyFloat fNan = new PyFloat(Double.NaN);
      PyFloat fInf = new PyFloat(Double.POSITIVE_INFINITY);
      PyFloat fNinf = new PyFloat(Double.NEGATIVE_INFINITY);
      assertEquals("nan", String.valueOf(fNan.__repr__()));
      assertEquals("inf", String.valueOf(fInf.__repr__()));
      assertEquals("-inf", String.valueOf(fNinf.__repr__()));
   }
}
