package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import java.util.Arrays;

public class ElementValuePairInfo implements IBinaryElementValuePair {
   static final ElementValuePairInfo[] NoMembers = new ElementValuePairInfo[0];
   private char[] name;
   private Object value;

   public ElementValuePairInfo(char[] name, Object value) {
      this.name = name;
      this.value = value;
   }

   public char[] getName() {
      return this.name;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.name);
      buffer.append('=');
      if (this.value instanceof Object[]) {
         Object[] values = (Object[])this.value;
         buffer.append('{');
         int i = 0;

         for(int l = values.length; i < l; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            buffer.append(values[i]);
         }

         buffer.append('}');
      } else {
         buffer.append(this.value);
      }

      return buffer.toString();
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + CharOperation.hashCode(this.name);
      result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
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
         ElementValuePairInfo other = (ElementValuePairInfo)obj;
         if (!Arrays.equals(this.name, other.name)) {
            return false;
         } else {
            if (this.value == null) {
               if (other.value != null) {
                  return false;
               }
            } else if (!this.value.equals(other.value)) {
               return false;
            }

            return true;
         }
      }
   }
}
