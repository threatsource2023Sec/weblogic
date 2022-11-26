package org.python.bouncycastle.openssl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.util.io.pem.PemGenerationException;
import org.python.bouncycastle.util.io.pem.PemObject;
import org.python.bouncycastle.util.io.pem.PemObjectGenerator;

public class PKCS8Generator implements PemObjectGenerator {
   public static final ASN1ObjectIdentifier AES_128_CBC;
   public static final ASN1ObjectIdentifier AES_192_CBC;
   public static final ASN1ObjectIdentifier AES_256_CBC;
   public static final ASN1ObjectIdentifier DES3_CBC;
   public static final ASN1ObjectIdentifier PBE_SHA1_RC4_128;
   public static final ASN1ObjectIdentifier PBE_SHA1_RC4_40;
   public static final ASN1ObjectIdentifier PBE_SHA1_3DES;
   public static final ASN1ObjectIdentifier PBE_SHA1_2DES;
   public static final ASN1ObjectIdentifier PBE_SHA1_RC2_128;
   public static final ASN1ObjectIdentifier PBE_SHA1_RC2_40;
   private PrivateKeyInfo key;
   private OutputEncryptor outputEncryptor;

   public PKCS8Generator(PrivateKeyInfo var1, OutputEncryptor var2) {
      this.key = var1;
      this.outputEncryptor = var2;
   }

   public PemObject generate() throws PemGenerationException {
      return this.outputEncryptor != null ? this.generate(this.key, this.outputEncryptor) : this.generate(this.key, (OutputEncryptor)null);
   }

   private PemObject generate(PrivateKeyInfo var1, OutputEncryptor var2) throws PemGenerationException {
      try {
         byte[] var3 = var1.getEncoded();
         if (var2 == null) {
            return new PemObject("PRIVATE KEY", var3);
         } else {
            ByteArrayOutputStream var4 = new ByteArrayOutputStream();
            OutputStream var5 = var2.getOutputStream(var4);
            var5.write(var1.getEncoded());
            var5.close();
            EncryptedPrivateKeyInfo var6 = new EncryptedPrivateKeyInfo(var2.getAlgorithmIdentifier(), var4.toByteArray());
            return new PemObject("ENCRYPTED PRIVATE KEY", var6.getEncoded());
         }
      } catch (IOException var7) {
         throw new PemGenerationException("unable to process encoded key data: " + var7.getMessage(), var7);
      }
   }

   static {
      AES_128_CBC = NISTObjectIdentifiers.id_aes128_CBC;
      AES_192_CBC = NISTObjectIdentifiers.id_aes192_CBC;
      AES_256_CBC = NISTObjectIdentifiers.id_aes256_CBC;
      DES3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
      PBE_SHA1_RC4_128 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4;
      PBE_SHA1_RC4_40 = PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4;
      PBE_SHA1_3DES = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC;
      PBE_SHA1_2DES = PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC;
      PBE_SHA1_RC2_128 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC;
      PBE_SHA1_RC2_40 = PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC;
   }
}
