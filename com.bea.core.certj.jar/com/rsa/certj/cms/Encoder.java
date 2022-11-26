package com.rsa.certj.cms;

import com.rsa.jsafe.crypto.FIPS140Context;
import java.io.IOException;
import java.io.OutputStream;

/** @deprecated */
public final class Encoder {
   private final com.rsa.jsafe.cms.Encoder a;
   private final FIPS140Context b;

   Encoder(com.rsa.jsafe.cms.Encoder var1, FIPS140Context var2) {
      this.a = var1;
      this.b = var2;
   }

   /** @deprecated */
   public ContentType getType() {
      return ContentType.getContentType(this.a.getType().getIdentifier());
   }

   /** @deprecated */
   public OutputStream getContentOutputStream(ContentType var1) throws CMSException {
      try {
         return this.a.getContentOutputStream(var1.a());
      } catch (IOException var3) {
         throw new CMSException(var3);
      }
   }

   /** @deprecated */
   public void copyContent(Decoder var1) throws CMSException {
      try {
         this.a.copyContent(var1.a());
      } catch (IOException var3) {
         throw new CMSException(var3);
      }
   }

   FIPS140Context a() {
      return this.b;
   }
}
