package org.python.compiler;

import java.io.IOException;
import org.python.core.PyUnicode;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyUnicodeConstant extends Constant implements ClassConstants, Opcodes {
   final String value;

   PyUnicodeConstant(String value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.ldc(this.value);
      c.invokestatic(CodegenUtils.p(PyUnicode.class), "fromInterned", CodegenUtils.sig(PyUnicode.class, String.class));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof PyUnicodeConstant ? ((PyUnicodeConstant)o).value.equals(this.value) : false;
   }
}
