package org.python.bouncycastle.pkcs.jcajce;

import java.io.OutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.EncryptionScheme;
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
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.operator.SecretKeySizeProvider;

public class JcePKCSPBEOutputEncryptorBuilder {
   private JcaJceHelper helper = new DefaultJcaJceHelper();
   private ASN1ObjectIdentifier algorithm;
   private ASN1ObjectIdentifier keyEncAlgorithm;
   private SecureRandom random;
   private SecretKeySizeProvider keySizeProvider;
   private int iterationCount;

   public JcePKCSPBEOutputEncryptorBuilder(ASN1ObjectIdentifier var1) {
      this.keySizeProvider = DefaultSecretKeySizeProvider.INSTANCE;
      this.iterationCount = 1024;
      if (this.isPKCS12(var1)) {
         this.algorithm = var1;
         this.keyEncAlgorithm = var1;
      } else {
         this.algorithm = PKCSObjectIdentifiers.id_PBES2;
         this.keyEncAlgorithm = var1;
      }

   }

   public JcePKCSPBEOutputEncryptorBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcePKCSPBEOutputEncryptorBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public JcePKCSPBEOutputEncryptorBuilder setKeySizeProvider(SecretKeySizeProvider var1) {
      this.keySizeProvider = var1;
      return this;
   }

   public JcePKCSPBEOutputEncryptorBuilder setIterationCount(int var1) {
      this.iterationCount = var1;
      return this;
   }

   public OutputEncryptor build(final char[] var1) throws OperatorCreationException {
      if (this.random == null) {
         this.random = new SecureRandom();
      }

      byte[] var2 = new byte[20];
      this.random.nextBytes(var2);

      try {
         final Cipher var3;
         final AlgorithmIdentifier var4;
         if (this.isPKCS12(this.algorithm)) {
            var3 = this.helper.createCipher(this.algorithm.getId());
            var3.init(1, new PKCS12KeyWithParameters(var1, var2, this.iterationCount));
            var4 = new AlgorithmIdentifier(this.algorithm, new PKCS12PBEParams(var2, this.iterationCount));
         } else {
            if (!this.algorithm.equals(PKCSObjectIdentifiers.id_PBES2)) {
               throw new OperatorCreationException("unrecognised algorithm");
            }

            SecretKeyFactory var5 = this.helper.createSecretKeyFactory(PKCSObjectIdentifiers.id_PBKDF2.getId());
            SecretKey var6 = var5.generateSecret(new PBEKeySpec(var1, var2, this.iterationCount, this.keySizeProvider.getKeySize(new AlgorithmIdentifier(this.keyEncAlgorithm))));
            var3 = this.helper.createCipher(this.keyEncAlgorithm.getId());
            var3.init(1, var6, this.random);
            PBES2Parameters var7 = new PBES2Parameters(new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(var2, this.iterationCount)), new EncryptionScheme(this.keyEncAlgorithm, ASN1Primitive.fromByteArray(var3.getParameters().getEncoded())));
            var4 = new AlgorithmIdentifier(this.algorithm, var7);
         }

         return new OutputEncryptor() {
            public AlgorithmIdentifier getAlgorithmIdentifier() {
               return var4;
            }

            public OutputStream getOutputStream(OutputStream var1x) {
               return new CipherOutputStream(var1x, var3);
            }

            public GenericKey getKey() {
               return JcePKCSPBEOutputEncryptorBuilder.this.isPKCS12(var4.getAlgorithm()) ? new GenericKey(var4, JcePKCSPBEOutputEncryptorBuilder.PKCS12PasswordToBytes(var1)) : new GenericKey(var4, JcePKCSPBEOutputEncryptorBuilder.PKCS5PasswordToBytes(var1));
            }
         };
      } catch (Exception var8) {
         throw new OperatorCreationException("unable to create OutputEncryptor: " + var8.getMessage(), var8);
      }
   }

   private boolean isPKCS12(ASN1ObjectIdentifier var1) {
      return var1.on(PKCSObjectIdentifiers.pkcs_12PbeIds) || var1.on(BCObjectIdentifiers.bc_pbe_sha1_pkcs12) || var1.on(BCObjectIdentifiers.bc_pbe_sha256_pkcs12);
   }

   private static byte[] PKCS5PasswordToBytes(char[] var0) {
      if (var0 == null) {
         return new byte[0];
      } else {
         byte[] var1 = new byte[var0.length];

         for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (byte)var0[var2];
         }

         return var1;
      }
   }

   private static byte[] PKCS12PasswordToBytes(char[] var0) {
      if (var0 != null && var0.length > 0) {
         byte[] var1 = new byte[(var0.length + 1) * 2];

         for(int var2 = 0; var2 != var0.length; ++var2) {
            var1[var2 * 2] = (byte)(var0[var2] >>> 8);
            var1[var2 * 2 + 1] = (byte)var0[var2];
         }

         return var1;
      } else {
         return new byte[0];
      }
   }
}
