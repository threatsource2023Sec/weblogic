package com.rsa.certj.cms;

import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public final class PasswordRecipientInfo extends RecipientInfo {
   private final char[] a;
   private final String b;
   private final int c;
   private com.rsa.jsafe.cms.PasswordRecipientInfo d;

   PasswordRecipientInfo(com.rsa.jsafe.cms.PasswordRecipientInfo var1) {
      this.d = var1;
      this.a = null;
      this.b = null;
      this.c = 0;
   }

   PasswordRecipientInfo(char[] var1, String var2, int var3) {
      this.d = null;
      this.a = var1 == null ? null : (char[])var1.clone();
      this.b = var2;
      this.c = var3;
   }

   com.rsa.jsafe.cms.RecipientInfo a(JsafeJCE var1) throws CMSException {
      if (this.d == null) {
         try {
            if (this.b == null) {
               this.d = com.rsa.jsafe.cms.InfoObjectFactory.newPasswordRecipientInfo(this.a);
            } else {
               String var2 = com.rsa.certj.cms.a.b(this.b);
               this.d = com.rsa.jsafe.cms.InfoObjectFactory.newPasswordRecipientInfo(this.a, var2, this.c);
            }
         } catch (com.rsa.jsafe.cms.CMSException var3) {
            throw new CMSException(var3);
         }
      }

      return this.d;
   }
}
