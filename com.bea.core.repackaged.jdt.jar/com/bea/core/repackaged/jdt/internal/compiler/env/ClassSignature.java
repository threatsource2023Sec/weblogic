package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.Arrays;

public class ClassSignature {
   char[] className;

   public ClassSignature(char[] className) {
      this.className = className;
   }

   public char[] getTypeName() {
      return this.className;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.className);
      buffer.append(".class");
      return buffer.toString();
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + CharOperation.hashCode(this.className);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ClassSignature other = (ClassSignature)obj;
         return Arrays.equals(this.className, other.className);
      }
   }
}
