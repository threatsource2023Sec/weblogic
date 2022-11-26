package org.python.compiler;

import java.io.IOException;
import org.python.core.Py;
import org.python.core.PyFloat;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyFloatConstant extends Constant implements ClassConstants, Opcodes {
   final double value;

   PyFloatConstant(double value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.ldc(new Double(this.value));
      c.invokestatic(CodegenUtils.p(Py.class), "newFloat", CodegenUtils.sig(PyFloat.class, Double.TYPE));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return (int)this.value;
   }

   public boolean equals(Object o) {
      if (o instanceof PyFloatConstant) {
         PyFloatConstant pyco = (PyFloatConstant)o;
         return Double.doubleToLongBits(pyco.value) == Double.doubleToLongBits(this.value);
      } else {
         return false;
      }
   }
}
