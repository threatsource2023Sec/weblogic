package org.python.bouncycastle.cert.crmf.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cert.crmf.CRMFException;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.operator.SecretKeySizeProvider;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceCRMFEncryptorBuilder {
   private static final SecretKeySizeProvider KEY_SIZE_PROVIDER;
   private final ASN1ObjectIdentifier encryptionOID;
   private final int keySize;
   private CRMFHelper helper;
   private SecureRandom random;

   public JceCRMFEncryptorBuilder(ASN1ObjectIdentifier var1) {
      this(var1, -1);
   }

   public JceCRMFEncryptorBuilder(ASN1ObjectIdentifier var1, int var2) {
      this.helper = new CRMFHelper(new DefaultJcaJceHelper());
      this.encryptionOID = var1;
      this.keySize = var2;
   }

   public JceCRMFEncryptorBuilder setProvider(Provider var1) {
      this.helper = new CRMFHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceCRMFEncryptorBuilder setProvider(String var1) {
      this.helper = new CRMFHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JceCRMFEncryptorBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public OutputEncryptor build() throws CRMFException {
      return new CRMFOutputEncryptor(this.encryptionOID, this.keySize, this.random);
   }

   static {
      KEY_SIZE_PROVIDER = DefaultSecretKeySizeProvider.INSTANCE;
   }

   private class CRMFOutputEncryptor implements OutputEncryptor {
      private SecretKey encKey;
      private AlgorithmIdentifier algorithmIdentifier;
      private Cipher cipher;

      CRMFOutputEncryptor(ASN1ObjectIdentifier var2, int var3, SecureRandom var4) throws CRMFException {
         KeyGenerator var5 = JceCRMFEncryptorBuilder.this.helper.createKeyGenerator(var2);
         if (var4 == null) {
            var4 = new SecureRandom();
         }

         if (var3 < 0) {
            var3 = JceCRMFEncryptorBuilder.KEY_SIZE_PROVIDER.getKeySize(var2);
         }

         if (var3 < 0) {
            var5.init(var4);
         } else {
            var5.init(var3, var4);
         }

         this.cipher = JceCRMFEncryptorBuilder.this.helper.createCipher(var2);
         this.encKey = var5.generateKey();
         AlgorithmParameters var6 = JceCRMFEncryptorBuilder.this.helper.generateParameters(var2, this.encKey, var4);

         try {
            this.cipher.init(1, this.encKey, var6, var4);
         } catch (GeneralSecurityException var8) {
            throw new CRMFException("unable to initialize cipher: " + var8.getMessage(), var8);
         }

         if (var6 == null) {
            var6 = this.cipher.getParameters();
         }

         this.algorithmIdentifier = JceCRMFEncryptorBuilder.this.helper.getAlgorithmIdentifier(var2, var6);
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return this.algorithmIdentifier;
      }

      public OutputStream getOutputStream(OutputStream var1) {
         return new CipherOutputStream(var1, this.cipher);
      }

      public GenericKey getKey() {
         return new JceGenericKey(this.algorithmIdentifier, this.encKey);
      }
   }
}
