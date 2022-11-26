package org.python.compiler;

import java.io.IOException;
import org.python.core.Py;
import org.python.core.PyComplex;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyComplexConstant extends Constant implements ClassConstants, Opcodes {
   final double value;

   PyComplexConstant(double value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.ldc(new Double(this.value));
      c.invokestatic(CodegenUtils.p(Py.class), "newImaginary", CodegenUtils.sig(PyComplex.class, Double.TYPE));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return (int)this.value;
   }

   public boolean equals(Object o) {
      if (o instanceof PyComplexConstant) {
         PyComplexConstant pyco = (PyComplexConstant)o;
         return Double.doubleToLongBits(pyco.value) == Double.doubleToLongBits(this.value);
      } else {
         return false;
      }
   }
}
