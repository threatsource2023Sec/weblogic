package org.python.bouncycastle.cms.jcajce;

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
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.operator.SecretKeySizeProvider;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceCMSContentEncryptorBuilder {
   private static final SecretKeySizeProvider KEY_SIZE_PROVIDER;
   private final ASN1ObjectIdentifier encryptionOID;
   private final int keySize;
   private EnvelopedDataHelper helper;
   private SecureRandom random;
   private AlgorithmParameters algorithmParameters;

   public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier var1) {
      this(var1, KEY_SIZE_PROVIDER.getKeySize(var1));
   }

   public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier var1, int var2) {
      this.helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
      this.encryptionOID = var1;
      int var3 = KEY_SIZE_PROVIDER.getKeySize(var1);
      if (var1.equals(PKCSObjectIdentifiers.des_EDE3_CBC)) {
         if (var2 != 168 && var2 != var3) {
            throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
         }

         this.keySize = 168;
      } else if (var1.equals(OIWObjectIdentifiers.desCBC)) {
         if (var2 != 56 && var2 != var3) {
            throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
         }

         this.keySize = 56;
      } else {
         if (var3 > 0 && var3 != var2) {
            throw new IllegalArgumentException("incorrect keySize for encryptionOID passed to builder.");
         }

         this.keySize = var2;
      }

   }

   public JceCMSContentEncryptorBuilder setProvider(Provider var1) {
      this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var1));
      return this;
   }

   public JceCMSContentEncryptorBuilder setProvider(String var1) {
      this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(var1));
      return this;
   }

   public JceCMSContentEncryptorBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public JceCMSContentEncryptorBuilder setAlgorithmParameters(AlgorithmParameters var1) {
      this.algorithmParameters = var1;
      return this;
   }

   public OutputEncryptor build() throws CMSException {
      return new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.algorithmParameters, this.random);
   }

   static {
      KEY_SIZE_PROVIDER = DefaultSecretKeySizeProvider.INSTANCE;
   }

   private class CMSOutputEncryptor implements OutputEncryptor {
      private SecretKey encKey;
      private AlgorithmIdentifier algorithmIdentifier;
      private Cipher cipher;

      CMSOutputEncryptor(ASN1ObjectIdentifier var2, int var3, AlgorithmParameters var4, SecureRandom var5) throws CMSException {
         KeyGenerator var6 = JceCMSContentEncryptorBuilder.this.helper.createKeyGenerator(var2);
         if (var5 == null) {
            var5 = new SecureRandom();
         }

         if (var3 < 0) {
            var6.init(var5);
         } else {
            var6.init(var3, var5);
         }

         this.cipher = JceCMSContentEncryptorBuilder.this.helper.createCipher(var2);
         this.encKey = var6.generateKey();
         if (var4 == null) {
            var4 = JceCMSContentEncryptorBuilder.this.helper.generateParameters(var2, this.encKey, var5);
         }

         try {
            this.cipher.init(1, this.encKey, var4, var5);
         } catch (GeneralSecurityException var8) {
            throw new CMSException("unable to initialize cipher: " + var8.getMessage(), var8);
         }

         if (var4 == null) {
            var4 = this.cipher.getParameters();
         }

         this.algorithmIdentifier = JceCMSContentEncryptorBuilder.this.helper.getAlgorithmIdentifier(var2, var4);
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
