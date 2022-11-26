package com.rsa.certj.cms;

import com.rsa.jsafe.crypto.FIPS140Context;
import java.io.IOException;
import java.io.InputStream;

/** @deprecated */
public abstract class Decoder {
   final com.rsa.jsafe.cms.Decoder a;
   final FIPS140Context b;
   private Decoder c;

   Decoder(com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      this.a = var1;
      this.b = var2;
   }

   /** @deprecated */
   public final ContentType getType() {
      return ContentType.getContentType(this.a.getType().getIdentifier());
   }

   /** @deprecated */
   public final ContentType getContentType() {
      return ContentType.getContentType(this.a.getContentType().getIdentifier());
   }

   /** @deprecated */
   public final InputStream getContentInputStream() throws IOException {
      try {
         return this.a.getContentInputStream();
      } catch (IOException var2) {
         throw new CMSException(var2);
      }
   }

   /** @deprecated */
   public final void streamContent() throws IOException {
      this.a.streamContent();
   }

   /** @deprecated */
   public final Decoder getContentDecoder() throws IOException {
      if (this.c == null) {
         ContentType var1 = ContentType.getContentType(this.a.getContentType().getIdentifier());
         this.c = CMS.a(var1, this.a.getContentDecoder(), this.b);
      }

      return this.c;
   }

   /** @deprecated */
   public final byte[] getContentBytes() throws IOException {
      return this.a.getContentBytes();
   }

   final com.rsa.jsafe.cms.Decoder a() {
      return this.a;
   }

   final FIPS140Context b() {
      return this.b;
   }
}
