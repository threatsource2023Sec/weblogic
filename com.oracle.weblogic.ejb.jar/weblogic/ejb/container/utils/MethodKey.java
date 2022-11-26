package weblogic.ejb.container.utils;

import java.lang.reflect.Method;

public final class MethodKey {
   private static final String[] EMPTY_ARRAY = new String[0];
   private final String methodName;
   private final String[] params;

   public MethodKey(String methodName, String[] params) {
      this.methodName = methodName;
      if (params == null) {
         this.params = EMPTY_ARRAY;
      } else {
         this.params = params;
      }

   }

   public MethodKey(Method m) {
      this.methodName = m.getName();
      Class[] args = m.getParameterTypes();
      this.params = new String[args.length];

      for(int i = 0; i < args.length; ++i) {
         this.params[i] = args[i].getName();
      }

   }

   public String getMethodName() {
      return this.methodName;
   }

   public String[] getMethodParams() {
      return this.params;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof MethodKey) {
         MethodKey other = (MethodKey)o;
         if (!this.methodName.equals(other.methodName)) {
            return false;
         } else if (other.params.length != this.params.length) {
            return false;
         } else {
            for(int i = 0; i < this.params.length; ++i) {
               if (!this.params[i].equals(other.params[i])) {
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
      int h = this.methodName.hashCode();
      String[] var2 = this.params;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String p = var2[var4];
         h ^= p.hashCode();
      }

      return h;
   }
}
