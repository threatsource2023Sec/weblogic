package org.python.bouncycastle.cms;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.KEKRecipientInfo;
import org.python.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import org.python.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import org.python.bouncycastle.asn1.cms.PasswordRecipientInfo;
import org.python.bouncycastle.asn1.cms.RecipientInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculator;

class CMSEnvelopedHelper {
   static RecipientInformationStore buildRecipientInformationStore(ASN1Set var0, AlgorithmIdentifier var1, CMSSecureReadable var2) {
      return buildRecipientInformationStore(var0, var1, var2, (AuthAttributesProvider)null);
   }

   static RecipientInformationStore buildRecipientInformationStore(ASN1Set var0, AlgorithmIdentifier var1, CMSSecureReadable var2, AuthAttributesProvider var3) {
      ArrayList var4 = new ArrayList();

      for(int var5 = 0; var5 != var0.size(); ++var5) {
         RecipientInfo var6 = RecipientInfo.getInstance(var0.getObjectAt(var5));
         readRecipientInfo(var4, var6, var1, var2, var3);
      }

      return new RecipientInformationStore(var4);
   }

   private static void readRecipientInfo(List var0, RecipientInfo var1, AlgorithmIdentifier var2, CMSSecureReadable var3, AuthAttributesProvider var4) {
      ASN1Encodable var5 = var1.getInfo();
      if (var5 instanceof KeyTransRecipientInfo) {
         var0.add(new KeyTransRecipientInformation((KeyTransRecipientInfo)var5, var2, var3, var4));
      } else if (var5 instanceof KEKRecipientInfo) {
         var0.add(new KEKRecipientInformation((KEKRecipientInfo)var5, var2, var3, var4));
      } else if (var5 instanceof KeyAgreeRecipientInfo) {
         KeyAgreeRecipientInformation.readRecipientInfo(var0, (KeyAgreeRecipientInfo)var5, var2, var3, var4);
      } else if (var5 instanceof PasswordRecipientInfo) {
         var0.add(new PasswordRecipientInformation((PasswordRecipientInfo)var5, var2, var3, var4));
      }

   }

   static class CMSAuthenticatedSecureReadable implements CMSSecureReadable {
      private AlgorithmIdentifier algorithm;
      private CMSReadable readable;

      CMSAuthenticatedSecureReadable(AlgorithmIdentifier var1, CMSReadable var2) {
         this.algorithm = var1;
         this.readable = var2;
      }

      public InputStream getInputStream() throws IOException, CMSException {
         return this.readable.getInputStream();
      }
   }

   static class CMSDigestAuthenticatedSecureReadable implements CMSSecureReadable {
      private DigestCalculator digestCalculator;
      private CMSReadable readable;

      public CMSDigestAuthenticatedSecureReadable(DigestCalculator var1, CMSReadable var2) {
         this.digestCalculator = var1;
         this.readable = var2;
      }

      public InputStream getInputStream() throws IOException, CMSException {
         return new FilterInputStream(this.readable.getInputStream()) {
            public int read() throws IOException {
               int var1 = this.in.read();
               if (var1 >= 0) {
                  CMSDigestAuthenticatedSecureReadable.this.digestCalculator.getOutputStream().write(var1);
               }

               return var1;
            }

            public int read(byte[] var1, int var2, int var3) throws IOException {
               int var4 = this.in.read(var1, var2, var3);
               if (var4 >= 0) {
                  CMSDigestAuthenticatedSecureReadable.this.digestCalculator.getOutputStream().write(var1, var2, var4);
               }

               return var4;
            }
         };
      }

      public byte[] getDigest() {
         return this.digestCalculator.getDigest();
      }
   }

   static class CMSEnvelopedSecureReadable implements CMSSecureReadable {
      private AlgorithmIdentifier algorithm;
      private CMSReadable readable;

      CMSEnvelopedSecureReadable(AlgorithmIdentifier var1, CMSReadable var2) {
         this.algorithm = var1;
         this.readable = var2;
      }

      public InputStream getInputStream() throws IOException, CMSException {
         return this.readable.getInputStream();
      }
   }
}
