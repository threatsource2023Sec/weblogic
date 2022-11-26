package org.python.bouncycastle.cms;

import org.python.bouncycastle.util.Arrays;

public class KEKRecipientId extends RecipientId {
   private byte[] keyIdentifier;

   public KEKRecipientId(byte[] var1) {
      super(1);
      this.keyIdentifier = var1;
   }

   public int hashCode() {
      return Arrays.hashCode(this.keyIdentifier);
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof KEKRecipientId)) {
         return false;
      } else {
         KEKRecipientId var2 = (KEKRecipientId)var1;
         return Arrays.areEqual(this.keyIdentifier, var2.keyIdentifier);
      }
   }

   public byte[] getKeyIdentifier() {
      return Arrays.clone(this.keyIdentifier);
   }

   public Object clone() {
      return new KEKRecipientId(this.keyIdentifier);
   }

   public boolean match(Object var1) {
      if (var1 instanceof byte[]) {
         return Arrays.areEqual(this.keyIdentifier, (byte[])((byte[])var1));
      } else {
         return var1 instanceof KEKRecipientInformation ? ((KEKRecipientInformation)var1).getRID().equals(this) : false;
      }
   }
}
