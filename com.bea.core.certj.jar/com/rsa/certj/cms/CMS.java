package com.rsa.certj.cms;

import com.rsa.certj.CertJ;
import com.rsa.jsafe.crypto.FIPS140Context;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** @deprecated */
public final class CMS {
   private static final String a = "ContentType cannot be null.";

   private CMS() {
   }

   /** @deprecated */
   public static Decoder newDecoder(ContentType var0, InputStream var1) throws IOException {
      return a(var0, (InputStream)var1, (CertJ)null);
   }

   /** @deprecated */
   public static Decoder newDecoder(ContentType var0, InputStream var1, CertJ var2) throws IOException {
      return a(var0, var1, var2);
   }

   /** @deprecated */
   public static Decoder newDetachedDecoder(ContentType var0, InputStream var1, InputStream var2) throws IOException {
      return a(var0, var1, var2, (CertJ)null);
   }

   /** @deprecated */
   public static Decoder newDetachedDecoder(ContentType var0, InputStream var1, InputStream var2, CertJ var3) throws IOException {
      return a(var0, var1, var2, var3);
   }

   /** @deprecated */
   public static Encoder newEncoder(ContentType var0, OutputStream var1) throws IOException {
      return a(var0, var1, (OutputStream)null, (CMSParameters)null, (CertJ)null);
   }

   /** @deprecated */
   public static Encoder newEncoder(ContentType var0, OutputStream var1, CMSParameters var2) throws IOException {
      return a(var0, var1, (OutputStream)null, var2, (CertJ)null);
   }

   /** @deprecated */
   public static Encoder newEncoder(ContentType var0, OutputStream var1, CertJ var2) throws IOException {
      return a(var0, var1, (OutputStream)null, (CMSParameters)null, var2);
   }

   /** @deprecated */
   public static Encoder newEncoder(ContentType var0, OutputStream var1, CMSParameters var2, CertJ var3) throws IOException {
      return a(var0, var1, (OutputStream)null, var2, var3);
   }

   /** @deprecated */
   public static Encoder newDetachedEncoder(ContentType var0, OutputStream var1, OutputStream var2, CMSParameters var3) throws IOException {
      if (var2 == null) {
         throw new IllegalArgumentException("Detached content stream cannot be null");
      } else {
         return b(var0, var1, var2, var3, (CertJ)null);
      }
   }

   /** @deprecated */
   public static Encoder newDetachedEncoder(ContentType var0, OutputStream var1, OutputStream var2, CMSParameters var3, CertJ var4) throws IOException {
      if (var2 == null) {
         throw new IllegalArgumentException("Detached content stream cannot be null");
      } else {
         return b(var0, var1, var2, var3, var4);
      }
   }

   /** @deprecated */
   public static Encoder newDetachedEncoder(ContentType var0, OutputStream var1, CMSParameters var2) throws IOException {
      return b(var0, var1, (OutputStream)null, var2, (CertJ)null);
   }

   /** @deprecated */
   public static Encoder newDetachedEncoder(ContentType var0, OutputStream var1, CMSParameters var2, CertJ var3) throws IOException {
      return b(var0, var1, (OutputStream)null, var2, var3);
   }

   private static Decoder a(ContentType var0, InputStream var1, CertJ var2) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("ContentType cannot be null.");
      } else {
         FIPS140Context var3 = com.rsa.certj.cms.a.a(var2);

         com.rsa.jsafe.cms.Decoder var4;
         try {
            var4 = com.rsa.jsafe.cms.CMS.newDecoder(var0.a(), var1, var3);
         } catch (IOException var6) {
            throw new CMSException(var6);
         }

         return a(var0, var4, var3);
      }
   }

   private static Decoder a(ContentType var0, InputStream var1, InputStream var2, CertJ var3) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("ContentType cannot be null.");
      } else {
         FIPS140Context var4 = com.rsa.certj.cms.a.a(var3);

         com.rsa.jsafe.cms.Decoder var5;
         try {
            var5 = com.rsa.jsafe.cms.CMS.newDetachedDecoder(var0.a(), var1, var2, var4);
         } catch (IOException var7) {
            throw new CMSException(var7);
         }

         return a(var0, var5, var4);
      }
   }

   private static Encoder a(ContentType var0, OutputStream var1, OutputStream var2, CMSParameters var3, CertJ var4) throws CMSException {
      if (var0 == null) {
         throw new IllegalArgumentException("ContentType cannot be null.");
      } else {
         FIPS140Context var5 = com.rsa.certj.cms.a.a(var4);
         com.rsa.jsafe.cms.CMSParameters var6 = var3 == null ? null : var3.a(var5);

         com.rsa.jsafe.cms.Encoder var7;
         try {
            var7 = com.rsa.jsafe.cms.CMS.newEncoder(var0.a(), var1, var6, var5);
         } catch (IOException var9) {
            throw new CMSException(var9);
         }

         return new Encoder(var7, var5);
      }
   }

   private static Encoder b(ContentType var0, OutputStream var1, OutputStream var2, CMSParameters var3, CertJ var4) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("ContentType cannot be null.");
      } else {
         FIPS140Context var6 = com.rsa.certj.cms.a.a(var4);
         com.rsa.jsafe.cms.CMSParameters var7 = var3 == null ? null : var3.a(var6);

         com.rsa.jsafe.cms.Encoder var5;
         try {
            if (var2 == null) {
               var5 = com.rsa.jsafe.cms.CMS.newDetachedEncoder(var0.a(), var1, var7, var6);
            } else {
               var5 = com.rsa.jsafe.cms.CMS.newDetachedEncoder(var0.a(), var1, var2, var7, var6);
            }
         } catch (IOException var9) {
            throw new CMSException(var9);
         }

         return new Encoder(var5, var6);
      }
   }

   static Decoder a(ContentType var0, com.rsa.jsafe.cms.Decoder var1, FIPS140Context var2) {
      if (var0 == ContentType.CONTENT_INFO) {
         return new ContentInfoDecoder(var1, var2);
      } else if (var0 == ContentType.AUTHENTICATED_DATA) {
         return new AuthenticatedDataDecoder(var1, var2);
      } else if (var0 == ContentType.DIGESTED_DATA) {
         return new DigestedDataDecoder(var1, var2);
      } else if (var0 == ContentType.ENCRYPTED_DATA) {
         return new EncryptedDataDecoder(var1, var2);
      } else if (var0 == ContentType.ENVELOPED_DATA) {
         return new EnvelopedDataDecoder(var1, var2);
      } else if (var0 == ContentType.SIGNED_DATA) {
         return new SignedDataDecoder(var1, var2);
      } else if (var0 == ContentType.TIMESTAMP_INFO) {
         return new TimeStampTokenDecoder(var1, var2);
      } else {
         throw new IllegalArgumentException("Unsupported content type");
      }
   }
}
