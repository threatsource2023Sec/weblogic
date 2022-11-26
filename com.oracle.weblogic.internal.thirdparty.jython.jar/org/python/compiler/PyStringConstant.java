package org.python.compiler;

import java.io.IOException;
import org.python.core.PyString;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyStringConstant extends Constant implements ClassConstants, Opcodes {
   final String value;

   PyStringConstant(String value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.ldc(this.value);
      c.invokestatic(CodegenUtils.p(PyString.class), "fromInterned", CodegenUtils.sig(PyString.class, String.class));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof PyStringConstant ? ((PyStringConstant)o).value.equals(this.value) : false;
   }
}
