package org.python.compiler;

import java.io.IOException;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyIntegerConstant extends Constant implements ClassConstants, Opcodes {
   final int value;

   PyIntegerConstant(int value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.iconst(this.value);
      c.invokestatic(CodegenUtils.p(Py.class), "newInteger", CodegenUtils.sig(PyInteger.class, Integer.TYPE));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return this.value;
   }

   public boolean equals(Object o) {
      if (o instanceof PyIntegerConstant) {
         return ((PyIntegerConstant)o).value == this.value;
      } else {
         return false;
      }
   }
}
