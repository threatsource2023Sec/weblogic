package com.rsa.certj.cms;

import com.rsa.certj.DatabaseService;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.cert.Attribute;
import com.rsa.jsafe.cert.X509ExtensionSpec;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;
import java.math.BigInteger;
import java.security.cert.CertStore;
import java.util.Date;
import javax.crypto.SecretKey;

/** @deprecated */
public abstract class CMSParameters {
   com.rsa.jsafe.cms.CMSParameters a;

   abstract com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException;

   static final class f extends CMSParameters {
      private String b;
      private String c;
      private byte[] d;
      private BigInteger e;
      private Date f;
      private Accuracy g;
      private boolean h;
      private BigInteger i;
      private GeneralName j;
      private X509V3Extensions k;

      public f(String var1, String var2, byte[] var3, byte[] var4, Date var5, Accuracy var6, boolean var7, byte[] var8, GeneralName var9, X509V3Extensions var10) {
         this.b = var1;
         this.c = var2;
         this.d = var3 == null ? null : (byte[])var3.clone();
         this.e = var4 == null ? null : new BigInteger(var4);
         this.f = var5 == null ? null : new Date(var5.getTime());
         this.g = var6;
         this.h = var7;
         this.i = var8 == null ? null : new BigInteger(var8);

         try {
            this.j = var9 == null ? null : (GeneralName)var9.clone();
            this.k = var10 == null ? null : (X509V3Extensions)var10.clone();
         } catch (CloneNotSupportedException var12) {
            throw new IllegalArgumentException(var12);
         }
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            com.rsa.jsafe.cms.Accuracy var2 = com.rsa.certj.cms.a.a(this.g);
            com.rsa.jsafe.cert.GeneralName var3 = com.rsa.certj.cms.a.a(this.j);
            X509ExtensionSpec var4 = com.rsa.certj.cms.a.a(this.k);
            this.a = com.rsa.jsafe.cms.ParameterFactory.newTimeStampTokenParameters(this.b, this.c, this.d, this.e, this.f, var2, this.h, this.i, var3, var4);
         }

         return this.a;
      }
   }

   static final class e extends CMSParameters {
      private SignerInfo[] b;
      private DatabaseService c;
      private JSAFE_SecureRandom d;

      public e(SignerInfo[] var1, DatabaseService var2, JSAFE_SecureRandom var3) {
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            JsafeJCE var2 = com.rsa.certj.cms.a.a(var1);
            com.rsa.jsafe.cms.SignerInfo[] var3 = com.rsa.certj.cms.a.a(this.b, var2);
            CertStore var4 = com.rsa.certj.cms.a.a(this.c, var2);
            this.a = com.rsa.jsafe.cms.ParameterFactory.newSignedDataParameters(var3, var4, this.d);
         }

         return this.a;
      }
   }

   static final class d extends CMSParameters {
      private RecipientInfo[] b;
      private String c;
      private int d;
      private DatabaseService e;
      private X501Attributes f;
      private JSAFE_SecureRandom g;

      public d(RecipientInfo[] var1, String var2, int var3, DatabaseService var4, X501Attributes var5, JSAFE_SecureRandom var6) {
         this.b = (RecipientInfo[])var1.clone();
         this.c = var2;
         this.d = var3;
         this.e = var4;

         try {
            this.f = var5 == null ? null : (X501Attributes)var5.clone();
         } catch (CloneNotSupportedException var8) {
            throw new IllegalArgumentException(var8);
         }

         this.g = var6;
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            JsafeJCE var2 = com.rsa.certj.cms.a.a(var1);
            com.rsa.jsafe.cms.RecipientInfo[] var3 = com.rsa.certj.cms.a.a(this.b, var2);
            CertStore var4 = com.rsa.certj.cms.a.a(this.e, var2);
            Attribute[] var5 = com.rsa.certj.cms.a.a(this.f);
            String var6 = com.rsa.certj.cms.a.a(this.c);
            this.a = com.rsa.jsafe.cms.ParameterFactory.newEnvelopedDataParameters(var3, var6, this.d, var4, var5, this.g);
         }

         return this.a;
      }
   }

   static final class c extends CMSParameters {
      private String b;
      private JSAFE_SecretKey c;
      private X501Attributes d;
      private JSAFE_SecureRandom e;

      c(String var1, JSAFE_SecretKey var2, X501Attributes var3, JSAFE_SecureRandom var4) {
         this.b = var1;

         try {
            this.c = var2 == null ? null : (JSAFE_SecretKey)var2.clone();
            this.d = var3 == null ? null : (X501Attributes)var3.clone();
         } catch (CloneNotSupportedException var6) {
            throw new IllegalArgumentException(var6);
         }

         this.e = var4;
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            JsafeJCE var2 = com.rsa.certj.cms.a.a(var1);
            SecretKey var3 = com.rsa.certj.cms.a.a(this.c, var2);
            Attribute[] var4 = com.rsa.certj.cms.a.a(this.d);
            String var5 = com.rsa.certj.cms.a.a(this.b);
            this.a = com.rsa.jsafe.cms.ParameterFactory.newEncryptedDataParameters(var5, var3, var4, this.e);
         }

         return this.a;
      }
   }

   static final class b extends CMSParameters {
      private String b;

      b(String var1) {
         this.b = var1;
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            this.a = com.rsa.jsafe.cms.ParameterFactory.newDigestedDataParameters(this.b);
         }

         return this.a;
      }
   }

   static final class a extends CMSParameters {
      private RecipientInfo[] b;
      private String c;
      private int d;
      private DatabaseService e;
      private X501Attributes f;
      private X501Attributes g;
      private JSAFE_SecureRandom h;

      a(RecipientInfo[] var1, String var2, int var3, DatabaseService var4, X501Attributes var5, X501Attributes var6, JSAFE_SecureRandom var7) {
         this.a(var1, var2);
         this.b = (RecipientInfo[])var1.clone();
         this.c = var2;
         this.d = var3;
         this.e = var4;

         try {
            this.f = var5 == null ? null : (X501Attributes)var5.clone();
            this.g = var6 == null ? null : (X501Attributes)var6.clone();
         } catch (CloneNotSupportedException var9) {
            throw new IllegalArgumentException(var9);
         }

         this.h = var7;
      }

      private void a(RecipientInfo[] var1, String var2) {
         if (var1 != null && var1.length >= 1) {
            if (var2 == null) {
               throw new IllegalArgumentException("MAC algorithm cannot be null.");
            }
         } else {
            throw new IllegalArgumentException("At least one recipient info object expected.");
         }
      }

      com.rsa.jsafe.cms.CMSParameters a(FIPS140Context var1) throws CMSException {
         if (this.a == null) {
            JsafeJCE var2 = com.rsa.certj.cms.a.a(var1);
            com.rsa.jsafe.cms.RecipientInfo[] var3 = com.rsa.certj.cms.a.a(this.b, var2);
            CertStore var4 = com.rsa.certj.cms.a.a(this.e, var2);
            Attribute[] var5 = com.rsa.certj.cms.a.a(this.f);
            Attribute[] var6 = com.rsa.certj.cms.a.a(this.g);
            this.a = com.rsa.jsafe.cms.ParameterFactory.newAuthenticatedDataParameters(var3, this.c, this.d, var4, var5, var6, this.h);
         }

         return this.a;
      }
   }
}
