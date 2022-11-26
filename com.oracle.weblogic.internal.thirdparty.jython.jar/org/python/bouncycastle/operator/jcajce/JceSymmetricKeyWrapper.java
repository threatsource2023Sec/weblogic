package org.python.bouncycastle.operator.jcajce;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.operator.SymmetricKeyWrapper;

public class JceSymmetricKeyWrapper extends SymmetricKeyWrapper {
   private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
   private SecureRandom random;
   private SecretKey wrappingKey;

   public JceSymmetricKeyWrapper(SecretKey var1) {
      super(determineKeyEncAlg(var1));
      this.wrappingKey = var1;
   }

   public JceSymmetricKeyWrapper setProvider(Provider var1) {
      this.helper = new OperatorHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public JceSymmetricKeyWrapper setProvider(String var1) {
      this.helper = new OperatorHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JceSymmetricKeyWrapper setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public byte[] generateWrappedKey(GenericKey var1) throws OperatorException {
      Key var2 = OperatorUtils.getJceKey(var1);
      Cipher var3 = this.helper.createSymmetricWrapper(this.getAlgorithmIdentifier().getAlgorithm());

      try {
         var3.init(3, this.wrappingKey, this.random);
         return var3.wrap(var2);
      } catch (GeneralSecurityException var5) {
         throw new OperatorException("cannot wrap key: " + var5.getMessage(), var5);
      }
   }

   private static AlgorithmIdentifier determineKeyEncAlg(SecretKey var0) {
      return determineKeyEncAlg(var0.getAlgorithm(), var0.getEncoded().length * 8);
   }

   static AlgorithmIdentifier determineKeyEncAlg(String var0, int var1) {
      if (!var0.startsWith("DES") && !var0.startsWith("TripleDES")) {
         if (var0.startsWith("RC2")) {
            return new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.3.7"), new ASN1Integer(58L));
         } else {
            ASN1ObjectIdentifier var2;
            if (var0.startsWith("AES")) {
               if (var1 == 128) {
                  var2 = NISTObjectIdentifiers.id_aes128_wrap;
               } else if (var1 == 192) {
                  var2 = NISTObjectIdentifiers.id_aes192_wrap;
               } else {
                  if (var1 != 256) {
                     throw new IllegalArgumentException("illegal keysize in AES");
                  }

                  var2 = NISTObjectIdentifiers.id_aes256_wrap;
               }

               return new AlgorithmIdentifier(var2);
            } else if (var0.startsWith("SEED")) {
               return new AlgorithmIdentifier(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
            } else if (var0.startsWith("Camellia")) {
               if (var1 == 128) {
                  var2 = NTTObjectIdentifiers.id_camellia128_wrap;
               } else if (var1 == 192) {
                  var2 = NTTObjectIdentifiers.id_camellia192_wrap;
               } else {
                  if (var1 != 256) {
                     throw new IllegalArgumentException("illegal keysize in Camellia");
                  }

                  var2 = NTTObjectIdentifiers.id_camellia256_wrap;
               }

               return new AlgorithmIdentifier(var2);
            } else {
               throw new IllegalArgumentException("unknown algorithm");
            }
         }
      } else {
         return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_CMS3DESwrap, DERNull.INSTANCE);
      }
   }
}
