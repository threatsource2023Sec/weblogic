package org.python.bouncycastle.openssl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.DSAParameter;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.cert.X509AttributeCertificateHolder;
import org.python.bouncycastle.cert.X509CRLHolder;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.python.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.io.pem.PemGenerationException;
import org.python.bouncycastle.util.io.pem.PemHeader;
import org.python.bouncycastle.util.io.pem.PemObject;
import org.python.bouncycastle.util.io.pem.PemObjectGenerator;

public class MiscPEMGenerator implements PemObjectGenerator {
   private static final ASN1ObjectIdentifier[] dsaOids;
   private static final byte[] hexEncodingTable;
   private final Object obj;
   private final PEMEncryptor encryptor;

   public MiscPEMGenerator(Object var1) {
      this.obj = var1;
      this.encryptor = null;
   }

   public MiscPEMGenerator(Object var1, PEMEncryptor var2) {
      this.obj = var1;
      this.encryptor = var2;
   }

   private PemObject createPemObject(Object var1) throws IOException {
      if (var1 instanceof PemObject) {
         return (PemObject)var1;
      } else if (var1 instanceof PemObjectGenerator) {
         return ((PemObjectGenerator)var1).generate();
      } else {
         String var2;
         byte[] var3;
         if (var1 instanceof X509CertificateHolder) {
            var2 = "CERTIFICATE";
            var3 = ((X509CertificateHolder)var1).getEncoded();
         } else if (var1 instanceof X509CRLHolder) {
            var2 = "X509 CRL";
            var3 = ((X509CRLHolder)var1).getEncoded();
         } else if (var1 instanceof X509TrustedCertificateBlock) {
            var2 = "TRUSTED CERTIFICATE";
            var3 = ((X509TrustedCertificateBlock)var1).getEncoded();
         } else if (var1 instanceof PrivateKeyInfo) {
            PrivateKeyInfo var4 = (PrivateKeyInfo)var1;
            ASN1ObjectIdentifier var5 = var4.getPrivateKeyAlgorithm().getAlgorithm();
            if (var5.equals(PKCSObjectIdentifiers.rsaEncryption)) {
               var2 = "RSA PRIVATE KEY";
               var3 = var4.parsePrivateKey().toASN1Primitive().getEncoded();
            } else if (!var5.equals(dsaOids[0]) && !var5.equals(dsaOids[1])) {
               if (!var5.equals(X9ObjectIdentifiers.id_ecPublicKey)) {
                  throw new IOException("Cannot identify private key");
               }

               var2 = "EC PRIVATE KEY";
               var3 = var4.parsePrivateKey().toASN1Primitive().getEncoded();
            } else {
               var2 = "DSA PRIVATE KEY";
               DSAParameter var6 = DSAParameter.getInstance(var4.getPrivateKeyAlgorithm().getParameters());
               ASN1EncodableVector var7 = new ASN1EncodableVector();
               var7.add(new ASN1Integer(0L));
               var7.add(new ASN1Integer(var6.getP()));
               var7.add(new ASN1Integer(var6.getQ()));
               var7.add(new ASN1Integer(var6.getG()));
               BigInteger var8 = ASN1Integer.getInstance(var4.parsePrivateKey()).getValue();
               BigInteger var9 = var6.getG().modPow(var8, var6.getP());
               var7.add(new ASN1Integer(var9));
               var7.add(new ASN1Integer(var8));
               var3 = (new DERSequence(var7)).getEncoded();
            }
         } else if (var1 instanceof SubjectPublicKeyInfo) {
            var2 = "PUBLIC KEY";
            var3 = ((SubjectPublicKeyInfo)var1).getEncoded();
         } else if (var1 instanceof X509AttributeCertificateHolder) {
            var2 = "ATTRIBUTE CERTIFICATE";
            var3 = ((X509AttributeCertificateHolder)var1).getEncoded();
         } else if (var1 instanceof PKCS10CertificationRequest) {
            var2 = "CERTIFICATE REQUEST";
            var3 = ((PKCS10CertificationRequest)var1).getEncoded();
         } else if (var1 instanceof PKCS8EncryptedPrivateKeyInfo) {
            var2 = "ENCRYPTED PRIVATE KEY";
            var3 = ((PKCS8EncryptedPrivateKeyInfo)var1).getEncoded();
         } else {
            if (!(var1 instanceof ContentInfo)) {
               throw new PemGenerationException("unknown object passed - can't encode.");
            }

            var2 = "PKCS7";
            var3 = ((ContentInfo)var1).getEncoded();
         }

         if (this.encryptor != null) {
            String var10 = Strings.toUpperCase(this.encryptor.getAlgorithm());
            if (var10.equals("DESEDE")) {
               var10 = "DES-EDE3-CBC";
            }

            byte[] var11 = this.encryptor.getIV();
            byte[] var12 = this.encryptor.encrypt(var3);
            ArrayList var13 = new ArrayList(2);
            var13.add(new PemHeader("Proc-Type", "4,ENCRYPTED"));
            var13.add(new PemHeader("DEK-Info", var10 + "," + this.getHexEncoded(var11)));
            return new PemObject(var2, var13, var12);
         } else {
            return new PemObject(var2, var3);
         }
      }
   }

   private String getHexEncoded(byte[] var1) throws IOException {
      char[] var2 = new char[var1.length * 2];

      for(int var3 = 0; var3 != var1.length; ++var3) {
         int var4 = var1[var3] & 255;
         var2[2 * var3] = (char)hexEncodingTable[var4 >>> 4];
         var2[2 * var3 + 1] = (char)hexEncodingTable[var4 & 15];
      }

      return new String(var2);
   }

   public PemObject generate() throws PemGenerationException {
      try {
         return this.createPemObject(this.obj);
      } catch (IOException var2) {
         throw new PemGenerationException("encoding exception: " + var2.getMessage(), var2);
      }
   }

   static {
      dsaOids = new ASN1ObjectIdentifier[]{X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1};
      hexEncodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
   }
}
