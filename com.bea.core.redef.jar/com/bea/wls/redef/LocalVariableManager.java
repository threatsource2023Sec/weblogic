package com.bea.wls.redef;

import java.util.HashMap;
import java.util.Map;
import serp.bytecode.BCClass;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;

public class LocalVariableManager {
   private Map localVariables = new HashMap();

   public boolean init(BCClass cls) {
      try {
         BCMethod[] var2 = cls.getDeclaredMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BCMethod method = var2[var4];
            Code code = method.getCode(false);
            if (code != null) {
               serp.bytecode.LocalVariableTable lvt = code.getLocalVariableTable(false);
               if (lvt != null) {
                  Key key = this.getKey(method);
                  this.localVariables.put(key, new LocalVariableTable(lvt));
               }
            }
         }

         return true;
      } catch (Exception var9) {
         return false;
      }
   }

   public boolean adjustLocalVariables(BCMethod method, int start, int previousEnd, int newEnd) {
      try {
         if (this.localVariables.isEmpty()) {
            return false;
         } else {
            Key key = this.getKey(method);
            LocalVariableTable lvt = (LocalVariableTable)this.localVariables.get(key);
            return lvt == null ? false : lvt.updateScope(start, previousEnd, newEnd);
         }
      } catch (Exception var7) {
         return false;
      }
   }

   public boolean insertLocalVariableTable(BCMethod method) {
      try {
         Key key = this.getKey(method);
         LocalVariableTable lvt = (LocalVariableTable)this.localVariables.get(key);
         return lvt == null ? false : lvt.insert(method);
      } catch (Exception var4) {
         return false;
      }
   }

   private Key getKey(BCMethod method) {
      String methodName = method.getName();
      String[] paramTypes = method.getParamNames();
      String returnType = method.getReturnName();
      return new Key(methodName, paramTypes, returnType);
   }

   public static class Key {
      private final String methodName;
      private final String returnType;
      private final String[] parameterTypes;

      public Key(String name, String[] parameterTypes, String returnType) {
         this.methodName = name;
         this.parameterTypes = parameterTypes;
         this.returnType = returnType;
      }

      public boolean equals(Object obj) {
         if (obj != null && obj instanceof Key) {
            Key other = (Key)obj;
            if (!this.methodName.equals(other.methodName)) {
               return false;
            } else if (!this.returnType.equals(other.returnType)) {
               return false;
            } else if (this.parameterTypes.length != other.parameterTypes.length) {
               return false;
            } else {
               for(int i = 0; i < this.parameterTypes.length; ++i) {
                  if (!this.parameterTypes[i].equals(other.parameterTypes[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = 17;
         result += 31 * this.methodName.hashCode();
         result += 31 * this.returnType.hashCode();

         for(int i = 0; i < this.parameterTypes.length; ++i) {
            result += 31 * this.parameterTypes[i].hashCode();
         }

         return result;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder(this.returnType);
         sb.append(" ").append(this.methodName).append("(");
         String[] var2 = this.parameterTypes;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String param = var2[var4];
            sb.append(param).append(", ");
         }

         sb.append(")");
         return sb.toString();
      }
   }
}
