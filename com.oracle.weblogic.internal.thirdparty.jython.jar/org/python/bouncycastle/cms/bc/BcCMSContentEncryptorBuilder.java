package org.python.bouncycastle.cms.bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSAlgorithm;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.io.CipherOutputStream;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OutputEncryptor;
import org.python.bouncycastle.util.Integers;

public class BcCMSContentEncryptorBuilder {
   private static Map keySizes = new HashMap();
   private final ASN1ObjectIdentifier encryptionOID;
   private final int keySize;
   private EnvelopedDataHelper helper;
   private SecureRandom random;

   private static int getKeySize(ASN1ObjectIdentifier var0) {
      Integer var1 = (Integer)keySizes.get(var0);
      return var1 != null ? var1 : -1;
   }

   public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier var1) {
      this(var1, getKeySize(var1));
   }

   public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier var1, int var2) {
      this.helper = new EnvelopedDataHelper();
      this.encryptionOID = var1;
      this.keySize = var2;
   }

   public BcCMSContentEncryptorBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public OutputEncryptor build() throws CMSException {
      return new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.random);
   }

   static {
      keySizes.put(CMSAlgorithm.AES128_CBC, Integers.valueOf(128));
      keySizes.put(CMSAlgorithm.AES192_CBC, Integers.valueOf(192));
      keySizes.put(CMSAlgorithm.AES256_CBC, Integers.valueOf(256));
      keySizes.put(CMSAlgorithm.CAMELLIA128_CBC, Integers.valueOf(128));
      keySizes.put(CMSAlgorithm.CAMELLIA192_CBC, Integers.valueOf(192));
      keySizes.put(CMSAlgorithm.CAMELLIA256_CBC, Integers.valueOf(256));
   }

   private class CMSOutputEncryptor implements OutputEncryptor {
      private KeyParameter encKey;
      private AlgorithmIdentifier algorithmIdentifier;
      private Object cipher;

      CMSOutputEncryptor(ASN1ObjectIdentifier var2, int var3, SecureRandom var4) throws CMSException {
         if (var4 == null) {
            var4 = new SecureRandom();
         }

         CipherKeyGenerator var5 = BcCMSContentEncryptorBuilder.this.helper.createKeyGenerator(var2, var4);
         this.encKey = new KeyParameter(var5.generateKey());
         this.algorithmIdentifier = BcCMSContentEncryptorBuilder.this.helper.generateAlgorithmIdentifier(var2, this.encKey, var4);
         BcCMSContentEncryptorBuilder.this.helper;
         this.cipher = EnvelopedDataHelper.createContentCipher(true, this.encKey, this.algorithmIdentifier);
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return this.algorithmIdentifier;
      }

      public OutputStream getOutputStream(OutputStream var1) {
         return this.cipher instanceof BufferedBlockCipher ? new CipherOutputStream(var1, (BufferedBlockCipher)this.cipher) : new CipherOutputStream(var1, (StreamCipher)this.cipher);
      }

      public GenericKey getKey() {
         return new GenericKey(this.algorithmIdentifier, this.encKey.getKey());
      }
   }
}
