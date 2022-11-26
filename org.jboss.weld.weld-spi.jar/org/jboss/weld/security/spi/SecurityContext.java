package org.jboss.weld.security.spi;

public interface SecurityContext extends AutoCloseable {
   SecurityContext NOOP_SECURITY_CONTEXT = new SecurityContext() {
      public void dissociate() {
      }

      public void close() {
      }

      public void associate() {
      }

      public String toString() {
         return "NOOP_SECURITY_CONTEXT";
      }
   };

   void associate();

   void dissociate();

   void close();
}
