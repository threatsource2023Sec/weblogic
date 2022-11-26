package org.python.bouncycastle.openssl.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.python.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceOpenSSLPKCS8EncryptorBuilder {
   public static final String AES_128_CBC;
   public static final String AES_192_CBC;
   public static final String AES_256_CBC;
   public static final String DES3_CBC;
   public static final String PBE_SHA1_RC4_128;
   public static final String PBE_SHA1_RC4_40;
   public static final String PBE_SHA1_3DES;
   public static final String PBE_SHA1_2DES;
   public static final String PBE_SHA1_RC2_128;
   public static final String PBE_SHA1_RC2_40;
   private JcaJceHelper helper = new DefaultJcaJceHelper();
   private AlgorithmParameters params;
   private ASN1ObjectIdentifier algOID;
   byte[] salt;
   int iterationCount;
   private Cipher cipher;
   private SecureRandom random;
   private AlgorithmParameterGenerator paramGen;
   private char[] password;
   private SecretKey key;

   public JceOpenSSLPKCS8EncryptorBuilder(ASN1ObjectIdentifier var1) {
      this.algOID = var1;
      this.iterationCount = 2048;
   }

   public JceOpenSSLPKCS8EncryptorBuilder setRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public JceOpenSSLPKCS8EncryptorBuilder setPasssword(char[] var1) {
      this.password = var1;
      return this;
   }

   public JceOpenSSLPKCS8EncryptorBuilder setIterationCount(int var1) {
      this.iterationCount = var1;
      return this;
   }

   public JceOpenSSLPKCS8EncryptorBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public JceOpenSSLPKCS8EncryptorBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public OutputEncryptor build() throws OperatorCreationException {
      this.salt = new byte[20];
      if (this.random == null) {
         this.random = new SecureRandom();
      }

      this.random.nextBytes(this.salt);

      try {
         this.cipher = this.helper.createCipher(this.algOID.getId());
         if (PEMUtilities.isPKCS5Scheme2(this.algOID)) {
            this.paramGen = this.helper.createAlgorithmParameterGenerator(this.algOID.getId());
         }
      } catch (GeneralSecurityException var8) {
         throw new OperatorCreationException(this.algOID + " not available: " + var8.getMessage(), var8);
      }

      final AlgorithmIdentifier var4;
      if (PEMUtilities.isPKCS5Scheme2(this.algOID)) {
         this.params = this.paramGen.generateParameters();

         try {
            KeyDerivationFunc var1 = new KeyDerivationFunc(this.algOID, ASN1Primitive.fromByteArray(this.params.getEncoded()));
            KeyDerivationFunc var2 = new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(this.salt, this.iterationCount));
            ASN1EncodableVector var3 = new ASN1EncodableVector();
            var3.add(var2);
            var3.add(var1);
            var4 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, PBES2Parameters.getInstance(new DERSequence(var3)));
         } catch (IOException var7) {
            throw new OperatorCreationException(var7.getMessage(), var7);
         }

         try {
            this.key = PEMUtilities.generateSecretKeyForPKCS5Scheme2(this.helper, this.algOID.getId(), this.password, this.salt, this.iterationCount);
            this.cipher.init(1, this.key, this.params);
         } catch (GeneralSecurityException var6) {
            throw new OperatorCreationException(var6.getMessage(), var6);
         }
      } else {
         if (!PEMUtilities.isPKCS12(this.algOID)) {
            throw new OperatorCreationException("unknown algorithm: " + this.algOID, (Throwable)null);
         }

         ASN1EncodableVector var9 = new ASN1EncodableVector();
         var9.add(new DEROctetString(this.salt));
         var9.add(new ASN1Integer((long)this.iterationCount));
         var4 = new AlgorithmIdentifier(this.algOID, PKCS12PBEParams.getInstance(new DERSequence(var9)));

         try {
            this.cipher.init(1, new PKCS12KeyWithParameters(this.password, this.salt, this.iterationCount));
         } catch (GeneralSecurityException var5) {
            throw new OperatorCreationException(var5.getMessage(), var5);
         }
      }

      return new OutputEncryptor() {
         public AlgorithmIdentifier getAlgorithmIdentifier() {
            return var4;
         }

         public OutputStream getOutputStream(OutputStream var1) {
            return new CipherOutputStream(var1, JceOpenSSLPKCS8EncryptorBuilder.this.cipher);
         }

         public GenericKey getKey() {
            return new JceGenericKey(var4, JceOpenSSLPKCS8EncryptorBuilder.this.key);
         }
      };
   }

   static {
      AES_128_CBC = NISTObjectIdentifiers.id_aes128_CBC.getId();
      AES_192_CBC = NISTObjectIdentifiers.id_aes192_CBC.getId();
      AES_256_CBC = NISTObjectIdentifiers.id_aes256_CBC.getId();
      DES3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC.getId();
      PBE_SHA1_RC4_128 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4.getId();
      PBE_SHA1_RC4_40 = PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4.getId();
      PBE_SHA1_3DES = PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC.getId();
      PBE_SHA1_2DES = PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC.getId();
      PBE_SHA1_RC2_128 = PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC.getId();
      PBE_SHA1_RC2_40 = PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC.getId();
   }
}
