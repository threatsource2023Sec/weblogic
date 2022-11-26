package org.python.compiler;

import junit.framework.TestCase;

public class ModuleTest extends TestCase {
   public void testPyFloatConstant_Zero() {
      PyFloatConstant positiveZero = new PyFloatConstant(0.0);
      PyFloatConstant negativeZero = new PyFloatConstant(-0.0);
      assertNotSame(positiveZero, negativeZero);
      assertFalse(positiveZero.equals(negativeZero));
   }
}
