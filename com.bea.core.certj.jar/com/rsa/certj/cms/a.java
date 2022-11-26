package com.rsa.certj.cms;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.x.c;
import com.rsa.jsafe.JSAFE_InvalidKeyException;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import com.rsa.jsafe.cert.Attribute;
import com.rsa.jsafe.cert.GeneralName;
import com.rsa.jsafe.cert.InvalidEncodingException;
import com.rsa.jsafe.cert.X509ExtensionSpec;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;
import java.io.ByteArrayInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

/** @deprecated */
class a {
   private static final int a = 8;

   static JsafeJCE a(FIPS140Context var0) {
      return var0 == null ? new JsafeJCE() : new JsafeJCE(var0);
   }

   static FIPS140Context a(CertJ var0) {
      return var0 == null ? c.a().a : CertJInternalHelper.context(var0).a;
   }

   static com.rsa.jsafe.cms.RecipientInfo[] a(RecipientInfo[] var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.length;
         com.rsa.jsafe.cms.RecipientInfo[] var3 = new com.rsa.jsafe.cms.RecipientInfo[var2];

         for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4] == null ? null : var0[var4].a(var1);
         }

         return var3;
      }
   }

   static com.rsa.jsafe.cms.SignerInfo[] a(SignerInfo[] var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.length;
         com.rsa.jsafe.cms.SignerInfo[] var3 = new com.rsa.jsafe.cms.SignerInfo[var2];

         for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4] == null ? null : var0[var4].a(var1);
         }

         return var3;
      }
   }

   static com.rsa.jsafe.cms.Accuracy a(Accuracy var0) {
      return var0 == null ? null : new com.rsa.jsafe.cms.Accuracy(var0.getSeconds(), var0.getMillis(), var0.getMicros());
   }

   static Attribute[] a(X501Attributes var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.getAttributeCount();
         Attribute[] var2 = new Attribute[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = a(var0.getAttributeByIndex(var3));
         }

         return var2;
      }
   }

   private static Attribute a(X501Attribute var0) throws CMSException {
      int var1 = var0.getDERLen(0);
      byte[] var2 = new byte[var1];

      try {
         var1 = var0.getDEREncoding(var2, 0, 0);
      } catch (AttributeException var4) {
         throw new CMSException(var4);
      }

      byte[] var3 = var2;
      if (var1 != var2.length) {
         var3 = new byte[var1];
         System.arraycopy(var2, 0, var3, 0, var1);
      }

      return new Attribute(var3);
   }

   static CertStore a(DatabaseService var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();

         try {
            for(Certificate var3 = var0.firstCertificate(); var3 != null; var3 = var0.nextCertificate()) {
               var2.add(var3);
            }
         } catch (NoServiceException var8) {
            throw new CMSException(var8);
         } catch (DatabaseException var9) {
            throw new CMSException(var9);
         }

         Certificate[] var10 = (Certificate[])var2.toArray(new Certificate[var2.size()]);
         List var4 = b(var10, var1);

         try {
            for(CRL var5 = var0.firstCRL(); var5 != null; var5 = var0.nextCRL()) {
               if (var5 instanceof X509CRL) {
                  var4.add(a((X509CRL)var5, var1));
               }
            }
         } catch (NoServiceException var6) {
            throw new CMSException(var6);
         } catch (DatabaseException var7) {
            throw new CMSException(var7);
         }

         return a(var4, var1);
      }
   }

   static CertStore a(Certificate[] var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         List var2 = b(var0, var1);
         return a(var2, var1);
      }
   }

   private static List b(Certificate[] var0, JsafeJCE var1) throws CMSException {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var0[var3] instanceof X509Certificate) {
            var2.add(a((X509Certificate)var0[var3], var1));
         }
      }

      return var2;
   }

   private static CertStore a(List var0, JsafeJCE var1) throws CMSException {
      try {
         CollectionCertStoreParameters var2 = new CollectionCertStoreParameters(var0);
         return CertStore.getInstance("Collection", var2, var1);
      } catch (NoSuchAlgorithmException var3) {
         throw new CMSException(var3);
      } catch (InvalidAlgorithmParameterException var4) {
         throw new CMSException(var4);
      }
   }

   static java.security.cert.X509Certificate a(X509Certificate var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.getDERLen(0);
         byte[] var3 = new byte[var2];

         try {
            var0.getDEREncoding(var3, 0, 0);
            ByteArrayInputStream var5 = new ByteArrayInputStream(var3);
            CertificateFactory var6 = CertificateFactory.getInstance("X509", var1);
            java.security.cert.X509Certificate var4 = (java.security.cert.X509Certificate)var6.generateCertificate(var5);
            return var4;
         } catch (CertificateException var7) {
            throw new CMSException(var7);
         } catch (java.security.cert.CertificateException var8) {
            throw new CMSException(var8);
         }
      }
   }

   private static java.security.cert.X509CRL a(X509CRL var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.getDERLen(0);
         byte[] var3 = new byte[var2];

         try {
            var0.getDEREncoding(var3, 0, 0);
            ByteArrayInputStream var5 = new ByteArrayInputStream(var3);
            CertificateFactory var6 = CertificateFactory.getInstance("X509", var1);
            java.security.cert.X509CRL var4 = (java.security.cert.X509CRL)var6.generateCRL(var5);
            return var4;
         } catch (CertificateException var7) {
            throw new CMSException(var7);
         } catch (java.security.cert.CertificateException var8) {
            throw new CMSException(var8);
         } catch (CRLException var9) {
            throw new CMSException(var9);
         }
      }
   }

   static SecretKey a(JSAFE_SecretKey var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         String var2 = var0.getAlgorithm();
         byte[] var3 = var0.getSecretKeyData();
         if (var2.indexOf("AES") >= 0) {
            var2 = "AES";
         } else if (var2.equals("3DES_EDE")) {
            var2 = "DESede";
            byte[] var4;
            if (CertJUtils.byteArraysEqual(var3, 0, 8, var3, 8, 8) && var3.length == 24 && CertJUtils.byteArraysEqual(var3, 8, 8, var3, 16, 8)) {
               var4 = new byte[8];
               System.arraycopy(var3, 0, var4, 0, var4.length);
               var0.overwrite(var3);
               var3 = var4;
            } else if (var3.length == 24 && CertJUtils.byteArraysEqual(var3, 0, 8, var3, 16, 8)) {
               var4 = new byte[16];
               System.arraycopy(var3, 0, var4, 0, var4.length);
               var0.overwrite(var3);
               var3 = var4;
            }
         }

         SecretKeySpec var5 = new SecretKeySpec(var3, var2);
         var0.overwrite(var3);
         return var5;
      }
   }

   static PrivateKey a(JSAFE_PrivateKey var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         String var2 = var0.getAlgorithm();

         try {
            byte[] var3 = var0.getKeyData(var2 + "PrivateKeyBER")[0];
            PKCS8EncodedKeySpec var4 = new PKCS8EncodedKeySpec(var3);
            KeyFactory var5 = KeyFactory.getInstance(var2, var1);
            return var5.generatePrivate(var4);
         } catch (JSAFE_UnimplementedException var6) {
            throw new CMSException(var6);
         } catch (NoSuchAlgorithmException var7) {
            throw new CMSException(var7);
         } catch (InvalidKeySpecException var8) {
            throw new CMSException(var8);
         }
      }
   }

   static PublicKey a(JSAFE_PublicKey var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         String var2 = var0.getAlgorithm();

         try {
            byte[] var3 = var0.getKeyData(var2 + "PublicKeyBER")[0];
            X509EncodedKeySpec var4 = new X509EncodedKeySpec(var3);
            KeyFactory var5 = KeyFactory.getInstance(var2, var1);
            return var5.generatePublic(var4);
         } catch (JSAFE_UnimplementedException var6) {
            throw new CMSException(var6);
         } catch (NoSuchAlgorithmException var7) {
            throw new CMSException(var7);
         } catch (InvalidKeySpecException var8) {
            throw new CMSException(var8);
         }
      }
   }

   static GeneralName a(com.rsa.certj.cert.extensions.GeneralName var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         try {
            int var1 = var0.getDERLen(0);
            byte[] var2 = new byte[var1];
            var0.getDEREncoding(var2, 0, 0);
            return new GeneralName(var2);
         } catch (NameException var3) {
            throw new CMSException(var3);
         } catch (InvalidEncodingException var4) {
            throw new CMSException(var4);
         }
      }
   }

   static com.rsa.jsafe.cms.KeyContainer a(KeyContainer var0, JsafeJCE var1) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         JSAFE_SecretKey var2 = var0.getSecretKey();
         char[] var3 = var0.getPassword();
         if (var3 != null) {
            return new com.rsa.jsafe.cms.KeyContainer(var3);
         } else if (var2 != null) {
            if (var2.getAlgorithm().indexOf("PBKDF2") >= 0) {
               try {
                  return new com.rsa.jsafe.cms.KeyContainer(var2.getPassword());
               } catch (JSAFE_InvalidKeyException var6) {
                  throw new CMSException(var6);
               }
            } else {
               SecretKey var7 = a(var2, var1);
               return new com.rsa.jsafe.cms.KeyContainer(var7);
            }
         } else {
            PrivateKey var4 = a(var0.getPrivateKey(), var1);
            PublicKey var5 = a(var0.getPublicKey(), var1);
            return new com.rsa.jsafe.cms.KeyContainer(var4, var5);
         }
      }
   }

   static X509ExtensionSpec a(X509V3Extensions var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         X509ExtensionSpec var1 = new X509ExtensionSpec();
         int var2 = var0.getExtensionCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            X509V3Extension var4;
            try {
               var4 = var0.getExtensionByIndex(var3);
            } catch (CertificateException var6) {
               throw new CMSException(var6);
            }

            byte[] var5 = new byte[var4.getDERLen(0)];
            var4.getDEREncoding(var5, 0, 0);
            var1.addOtherExtension(var5);
         }

         return var1;
      }
   }

   static String a(String var0) {
      if (var0 == null) {
         return null;
      } else {
         String[] var1 = var0.split("/");
         if ("3DES_EDE".equals(var1[0])) {
            var1[0] = "DESede";
         } else if (var1[0].startsWith("AES")) {
            var1[0] = "AES";
         }

         StringBuilder var2 = new StringBuilder(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append('/');
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   static String b(String var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         String[] var1 = var0.toUpperCase().split("/");
         if (var1.length == 3 && var1[0].equals("PBKDF2") && var1[2].startsWith("PKCS5V2PBE")) {
            String var2;
            if ("SHA1".equals(var1[1])) {
               var2 = "PBKDF2withSHA1";
            } else if ("SHA256".equals(var1[1])) {
               var2 = "PBKDF2withSHA256";
            } else if ("SHA512".equals(var1[1])) {
               var2 = "PBKDF2withSHA512";
            } else if ("SHA384".equals(var1[1])) {
               var2 = "PBKDF2withSHA384";
            } else {
               if (!"SHA224".equals(var1[1])) {
                  throw new CMSException("Illegal hash algorithm string: " + var1[1]);
               }

               var2 = "PBKDF2withSHA224";
            }

            return var2;
         } else {
            throw new CMSException("Illegal KDF algorithm string: " + var0);
         }
      }
   }

   static RecipientInfo[] a(com.rsa.jsafe.cms.RecipientInfo[] var0) {
      if (var0 == null) {
         return null;
      } else {
         RecipientInfo[] var1 = new RecipientInfo[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = a(var0[var2]);
         }

         return var1;
      }
   }

   private static RecipientInfo a(com.rsa.jsafe.cms.RecipientInfo var0) {
      Object var1;
      if (var0 instanceof com.rsa.jsafe.cms.KeyAgreeRecipientInfo) {
         var1 = new KeyAgreeRecipientInfo((com.rsa.jsafe.cms.KeyAgreeRecipientInfo)var0);
      } else if (var0 instanceof com.rsa.jsafe.cms.KekRecipientInfo) {
         var1 = new KekRecipientInfo((com.rsa.jsafe.cms.KekRecipientInfo)var0);
      } else if (var0 instanceof com.rsa.jsafe.cms.KeyTransRecipientInfo) {
         var1 = new KeyTransRecipientInfo((com.rsa.jsafe.cms.KeyTransRecipientInfo)var0);
      } else {
         if (!(var0 instanceof com.rsa.jsafe.cms.PasswordRecipientInfo)) {
            throw new IllegalArgumentException("RecipientInfo type not supported.");
         }

         var1 = new PasswordRecipientInfo((com.rsa.jsafe.cms.PasswordRecipientInfo)var0);
      }

      return (RecipientInfo)var1;
   }

   static SignerInfo[] a(com.rsa.jsafe.cms.SignerInfo[] var0) {
      if (var0 == null) {
         return null;
      } else {
         SignerInfo[] var1 = new SignerInfo[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new SignerInfo(var0[var2]);
         }

         return var1;
      }
   }

   static X509Certificate[] a(java.security.cert.X509Certificate[] var0) {
      if (var0 == null) {
         return null;
      } else {
         X509Certificate[] var1 = new X509Certificate[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            try {
               byte[] var3 = var0[var2].getEncoded();
               var1[var2] = new X509Certificate(var3, 0, 0);
            } catch (CertificateEncodingException var5) {
               throw new InvalidEncodingException(var5);
            } catch (CertificateException var6) {
               throw new InvalidEncodingException(var6);
            }
         }

         return var1;
      }
   }

   static X509CRL[] a(java.security.cert.X509CRL[] var0) {
      if (var0 == null) {
         return null;
      } else {
         X509CRL[] var1 = new X509CRL[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            try {
               byte[] var3 = var0[var2].getEncoded();
               var1[var2] = new X509CRL(var3, 0, 0);
            } catch (CRLException var5) {
               throw new InvalidEncodingException(var5);
            } catch (CertificateException var6) {
               throw new InvalidEncodingException(var6);
            }
         }

         return var1;
      }
   }

   static X501Attributes a(Attribute[] var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         X501Attributes var1 = new X501Attributes();

         for(int var2 = 0; var2 < var0.length; ++var2) {
            try {
               var1.addAttribute(X501Attribute.getInstance(var0[var2].getEncoded(), 0, 0));
            } catch (AttributeException var4) {
               throw new CMSException(var4);
            }
         }

         return var1;
      }
   }

   static JSAFE_PublicKey a(PublicKey var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         byte[] var1 = var0.getEncoded();

         try {
            return JSAFE_PublicKey.getInstance(var1, 0, "Java");
         } catch (JSAFE_UnimplementedException var3) {
            throw new CMSException(var3);
         } catch (JSAFE_InvalidParameterException var4) {
            throw new CMSException(var4);
         }
      }
   }

   static X500Name a(X500Principal var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         byte[] var1 = var0.getEncoded();

         try {
            return new X500Name(var1, 0, 0);
         } catch (NameException var3) {
            throw new CMSException(var3);
         }
      }
   }

   static com.rsa.certj.cert.extensions.GeneralName a(GeneralName var0) throws CMSException {
      if (var0 == null) {
         return null;
      } else {
         byte[] var1 = var0.getEncoded();

         try {
            return new com.rsa.certj.cert.extensions.GeneralName(var1, 0, 0);
         } catch (NameException var3) {
            throw new CMSException(var3);
         }
      }
   }
}
