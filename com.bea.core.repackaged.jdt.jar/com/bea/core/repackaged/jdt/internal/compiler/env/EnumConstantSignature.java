package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import java.util.Arrays;

public class EnumConstantSignature {
   char[] typeName;
   char[] constName;

   public EnumConstantSignature(char[] typeName, char[] constName) {
      this.typeName = typeName;
      this.constName = constName;
   }

   public char[] getTypeName() {
      return this.typeName;
   }

   public char[] getEnumConstantName() {
      return this.constName;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.typeName);
      buffer.append('.');
      buffer.append(this.constName);
      return buffer.toString();
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + CharOperation.hashCode(this.constName);
      result = 31 * result + CharOperation.hashCode(this.typeName);
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
         EnumConstantSignature other = (EnumConstantSignature)obj;
         return !Arrays.equals(this.constName, other.constName) ? false : Arrays.equals(this.typeName, other.typeName);
      }
   }
}
