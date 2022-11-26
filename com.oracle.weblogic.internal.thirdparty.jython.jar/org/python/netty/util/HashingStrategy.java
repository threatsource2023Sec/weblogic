package org.python.netty.util;

public interface HashingStrategy {
   HashingStrategy JAVA_HASHER = new HashingStrategy() {
      public int hashCode(Object obj) {
         return obj != null ? obj.hashCode() : 0;
      }

      public boolean equals(Object a, Object b) {
         return a == b || a != null && a.equals(b);
      }
   };

   int hashCode(Object var1);

   boolean equals(Object var1, Object var2);
}
