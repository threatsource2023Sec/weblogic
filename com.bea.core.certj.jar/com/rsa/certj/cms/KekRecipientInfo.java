package com.rsa.certj.cms;

import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.provider.JsafeJCE;
import java.util.Date;
import javax.crypto.SecretKey;

/** @deprecated */
public final class KekRecipientInfo extends RecipientInfo {
   private com.rsa.jsafe.cms.KekRecipientInfo a;
   private byte[] b;
   private JSAFE_SecretKey c;
   private Date d;
   private String e;
   private byte[] f;

   KekRecipientInfo(com.rsa.jsafe.cms.KekRecipientInfo var1) {
      this.a = var1;
   }

   KekRecipientInfo(byte[] var1, JSAFE_SecretKey var2, Date var3, String var4, byte[] var5) {
      this.b = var1 == null ? null : (byte[])var1.clone();

      try {
         this.c = (JSAFE_SecretKey)((JSAFE_SecretKey)(var2 == null ? null : var2.clone()));
      } catch (CloneNotSupportedException var7) {
         throw new IllegalArgumentException(var7);
      }

      this.d = var3 == null ? null : new Date(var3.getTime());
      this.e = var4;
      this.f = var5 == null ? null : (byte[])var5.clone();
   }

   /** @deprecated */
   public byte[] getKekId() {
      return this.a.getKekId();
   }

   /** @deprecated */
   public Date getKekIdDate() {
      return this.a.getKekIdDate();
   }

   /** @deprecated */
   public String getKekIdOtherAttrOId() {
      return this.a.getKekIdOtherAttrOId();
   }

   /** @deprecated */
   public byte[] getKekIdOtherAttr() {
      return this.a.getKekIdOtherAttr();
   }

   com.rsa.jsafe.cms.RecipientInfo a(JsafeJCE var1) throws CMSException {
      if (this.a == null) {
         SecretKey var2 = com.rsa.certj.cms.a.a(this.c, var1);

         try {
            if (this.e == null) {
               this.a = com.rsa.jsafe.cms.InfoObjectFactory.newKekRecipientInfo(this.b, var2, this.d);
            } else {
               this.a = com.rsa.jsafe.cms.InfoObjectFactory.newKekRecipientInfo(this.b, var2, this.d, this.e, this.f);
            }
         } catch (com.rsa.jsafe.cms.CMSException var4) {
            throw new CMSException(var4);
         }
      }

      return this.a;
   }
}
