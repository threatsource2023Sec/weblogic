package com.rsa.certj.x;

import com.rsa.certj.cert.AttributeValueAssertion;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.RDN;
import com.rsa.certj.cert.X500Name;
import com.rsa.jsafe.crypto.FIPS140Context;
import com.rsa.jsafe.provider.JsafeJCE;
import java.io.ByteArrayInputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class d {
   public static final String a = "Cp1252";

   public static X509Certificate a(byte[] var0, c var1) throws CertificateException {
      if (var1 == null) {
         var1 = c.a();
      }

      JsafeJCE var2;
      if (var1.b() != -1 && var1.c() != -1) {
         var2 = new JsafeJCE(new FIPS140Context(var1.b(), var1.c()));
      } else {
         var2 = new JsafeJCE();
      }

      CertificateFactory var3 = CertificateFactory.getInstance("X.509", var2);
      ByteArrayInputStream var4 = new ByteArrayInputStream(var0);
      return (X509Certificate)var3.generateCertificate(var4);
   }

   public static X509CRL b(byte[] var0, c var1) throws CRLException {
      if (var1 == null) {
         var1 = c.a();
      }

      JsafeJCE var2;
      if (var1.b() != -1 && var1.c() != -1) {
         var2 = new JsafeJCE(new FIPS140Context(var1.b(), var1.c()));
      } else {
         var2 = new JsafeJCE();
      }

      CertificateFactory var3;
      try {
         var3 = CertificateFactory.getInstance("X509", var2);
      } catch (CertificateException var5) {
         throw new CRLException(var5.getMessage());
      }

      ByteArrayInputStream var4 = new ByteArrayInputStream(var0);
      return (X509CRL)var3.generateCRL(var4);
   }

   public static void a(X500Name var0) {
      try {
         boolean var1 = false;

         for(int var2 = 0; var2 < var0.getRDNCount(); ++var2) {
            RDN var3 = var0.getRDN(var2);
            AttributeValueAssertion var4 = var3.getAttribute(14);
            if (var4 != null) {
               var1 = true;
               break;
            }
         }

         if (!var1) {
            RDN var6 = new RDN();
            AttributeValueAssertion var7 = new AttributeValueAssertion(14, AttributeValueAssertion.SERIAL_NUMBER_OID, 1280, (String)null);
            var6.addNameAVA(var7);
            var0.addRDN(var6);
         }
      } catch (NameException var5) {
      }

   }
}
