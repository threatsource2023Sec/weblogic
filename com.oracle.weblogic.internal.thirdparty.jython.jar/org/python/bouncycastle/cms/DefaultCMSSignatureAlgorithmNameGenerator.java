package org.python.bouncycastle.cms;

import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.bsi.BSIObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.eac.EACObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;

public class DefaultCMSSignatureAlgorithmNameGenerator implements CMSSignatureAlgorithmNameGenerator {
   private final Map encryptionAlgs = new HashMap();
   private final Map digestAlgs = new HashMap();

   private void addEntries(ASN1ObjectIdentifier var1, String var2, String var3) {
      this.digestAlgs.put(var1, var2);
      this.encryptionAlgs.put(var1, var3);
   }

   public DefaultCMSSignatureAlgorithmNameGenerator() {
      this.addEntries(NISTObjectIdentifiers.dsa_with_sha224, "SHA224", "DSA");
      this.addEntries(NISTObjectIdentifiers.dsa_with_sha256, "SHA256", "DSA");
      this.addEntries(NISTObjectIdentifiers.dsa_with_sha384, "SHA384", "DSA");
      this.addEntries(NISTObjectIdentifiers.dsa_with_sha512, "SHA512", "DSA");
      this.addEntries(OIWObjectIdentifiers.dsaWithSHA1, "SHA1", "DSA");
      this.addEntries(OIWObjectIdentifiers.md4WithRSA, "MD4", "RSA");
      this.addEntries(OIWObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
      this.addEntries(OIWObjectIdentifiers.md5WithRSA, "MD5", "RSA");
      this.addEntries(OIWObjectIdentifiers.sha1WithRSA, "SHA1", "RSA");
      this.addEntries(PKCSObjectIdentifiers.md2WithRSAEncryption, "MD2", "RSA");
      this.addEntries(PKCSObjectIdentifiers.md4WithRSAEncryption, "MD4", "RSA");
      this.addEntries(PKCSObjectIdentifiers.md5WithRSAEncryption, "MD5", "RSA");
      this.addEntries(PKCSObjectIdentifiers.sha1WithRSAEncryption, "SHA1", "RSA");
      this.addEntries(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224", "RSA");
      this.addEntries(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256", "RSA");
      this.addEntries(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384", "RSA");
      this.addEntries(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512", "RSA");
      this.addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128", "RSA");
      this.addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, "RIPEMD160", "RSA");
      this.addEntries(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256", "RSA");
      this.addEntries(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1", "ECDSA");
      this.addEntries(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224", "ECDSA");
      this.addEntries(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256", "ECDSA");
      this.addEntries(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384", "ECDSA");
      this.addEntries(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512", "ECDSA");
      this.addEntries(X9ObjectIdentifiers.id_dsa_with_sha1, "SHA1", "DSA");
      this.addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1", "ECDSA");
      this.addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224", "ECDSA");
      this.addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256", "ECDSA");
      this.addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384", "ECDSA");
      this.addEntries(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512", "ECDSA");
      this.addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_1, "SHA1", "RSA");
      this.addEntries(EACObjectIdentifiers.id_TA_RSA_v1_5_SHA_256, "SHA256", "RSA");
      this.addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_1, "SHA1", "RSAandMGF1");
      this.addEntries(EACObjectIdentifiers.id_TA_RSA_PSS_SHA_256, "SHA256", "RSAandMGF1");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA1, "SHA1", "PLAIN-ECDSA");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA224, "SHA224", "PLAIN-ECDSA");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA256, "SHA256", "PLAIN-ECDSA");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA384, "SHA384", "PLAIN-ECDSA");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_SHA512, "SHA512", "PLAIN-ECDSA");
      this.addEntries(BSIObjectIdentifiers.ecdsa_plain_RIPEMD160, "RIPEMD160", "PLAIN-ECDSA");
      this.encryptionAlgs.put(X9ObjectIdentifiers.id_dsa, "DSA");
      this.encryptionAlgs.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
      this.encryptionAlgs.put(TeleTrusTObjectIdentifiers.teleTrusTRSAsignatureAlgorithm, "RSA");
      this.encryptionAlgs.put(X509ObjectIdentifiers.id_ea_rsa, "RSA");
      this.encryptionAlgs.put(PKCSObjectIdentifiers.id_RSASSA_PSS, "RSAandMGF1");
      this.encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3410_94, "GOST3410");
      this.encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3410_2001, "ECGOST3410");
      this.encryptionAlgs.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.6.2"), "ECGOST3410");
      this.encryptionAlgs.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.1.5"), "GOST3410");
      this.encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "ECGOST3410");
      this.encryptionAlgs.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3410");
      this.digestAlgs.put(PKCSObjectIdentifiers.md2, "MD2");
      this.digestAlgs.put(PKCSObjectIdentifiers.md4, "MD4");
      this.digestAlgs.put(PKCSObjectIdentifiers.md5, "MD5");
      this.digestAlgs.put(OIWObjectIdentifiers.idSHA1, "SHA1");
      this.digestAlgs.put(NISTObjectIdentifiers.id_sha224, "SHA224");
      this.digestAlgs.put(NISTObjectIdentifiers.id_sha256, "SHA256");
      this.digestAlgs.put(NISTObjectIdentifiers.id_sha384, "SHA384");
      this.digestAlgs.put(NISTObjectIdentifiers.id_sha512, "SHA512");
      this.digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
      this.digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd160, "RIPEMD160");
      this.digestAlgs.put(TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
      this.digestAlgs.put(CryptoProObjectIdentifiers.gostR3411, "GOST3411");
      this.digestAlgs.put(new ASN1ObjectIdentifier("1.3.6.1.4.1.5849.1.2.1"), "GOST3411");
   }

   private String getDigestAlgName(ASN1ObjectIdentifier var1) {
      String var2 = (String)this.digestAlgs.get(var1);
      return var2 != null ? var2 : var1.getId();
   }

   private String getEncryptionAlgName(ASN1ObjectIdentifier var1) {
      String var2 = (String)this.encryptionAlgs.get(var1);
      return var2 != null ? var2 : var1.getId();
   }

   protected void setSigningEncryptionAlgorithmMapping(ASN1ObjectIdentifier var1, String var2) {
      this.encryptionAlgs.put(var1, var2);
   }

   protected void setSigningDigestAlgorithmMapping(ASN1ObjectIdentifier var1, String var2) {
      this.digestAlgs.put(var1, var2);
   }

   public String getSignatureName(AlgorithmIdentifier var1, AlgorithmIdentifier var2) {
      String var3 = this.getDigestAlgName(var2.getAlgorithm());
      return !var3.equals(var2.getAlgorithm().getId()) ? var3 + "with" + this.getEncryptionAlgName(var2.getAlgorithm()) : this.getDigestAlgName(var1.getAlgorithm()) + "with" + this.getEncryptionAlgName(var2.getAlgorithm());
   }
}
