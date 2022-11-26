package org.python.bouncycastle.dvcs;

import org.python.bouncycastle.asn1.x509.DigestInfo;

public class MessageImprint {
   private final DigestInfo messageImprint;

   public MessageImprint(DigestInfo var1) {
      this.messageImprint = var1;
   }

   public DigestInfo toASN1Structure() {
      return this.messageImprint;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         return var1 instanceof MessageImprint ? this.messageImprint.equals(((MessageImprint)var1).messageImprint) : false;
      }
   }

   public int hashCode() {
      return this.messageImprint.hashCode();
   }
}
