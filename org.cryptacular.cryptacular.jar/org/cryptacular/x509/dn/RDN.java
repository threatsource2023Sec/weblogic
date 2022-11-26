package org.cryptacular.x509.dn;

public class RDN {
   private final Attributes attributes;

   public RDN(Attributes attributes) {
      if (attributes == null) {
         throw new IllegalArgumentException("Attributes cannot be null");
      } else {
         this.attributes = attributes;
      }
   }

   public Attributes getAttributes() {
      return this.attributes;
   }
}
