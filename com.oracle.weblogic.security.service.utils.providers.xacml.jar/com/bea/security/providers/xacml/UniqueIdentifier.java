package com.bea.security.providers.xacml;

public class UniqueIdentifier {
   private int count = 0;
   private String prefix;

   public UniqueIdentifier(String prefix) {
      this.prefix = prefix;
   }

   public String getNext() {
      synchronized(this) {
         return this.prefix + String.valueOf(this.count++);
      }
   }
}
