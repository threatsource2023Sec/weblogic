package org.python.bouncycastle.cms;

public class PasswordRecipientId extends RecipientId {
   public PasswordRecipientId() {
      super(3);
   }

   public int hashCode() {
      return 3;
   }

   public boolean equals(Object var1) {
      return var1 instanceof PasswordRecipientId;
   }

   public Object clone() {
      return new PasswordRecipientId();
   }

   public boolean match(Object var1) {
      return var1 instanceof PasswordRecipientInformation;
   }
}
