package org.python.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.jcajce.io.MacOutputStream;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.jcajce.JceGenericKey;

public class JceCMSMacCalculatorBuilder {
   private final ASN1ObjectIdentifier macOID;
   private final int keySize;
   private EnvelopedDataHelper helper;
   private AlgorithmParameters algorithmParameters;
   private SecureRandom random;

   public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier var1) {
      this(var1, -1);
   }

   public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier var1, int var2) {
      this.helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
      this.macOID = var1;
      this.keySize = var2;
   }

   public JceCMSMacCalculatorBuilder setProvider(Provider var1) {
      this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var1));
      return this;
   }

   public JceCMSMacCalculatorBuilder setProvider(String var1) {
      this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(var1));
      return this;
   }

   public JceCMSMacCalculatorBuilder setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public JceCMSMacCalculatorBuilder setAlgorithmParameters(AlgorithmParameters var1) {
      this.algorithmParameters = var1;
      return this;
   }

   public MacCalculator build() throws CMSException {
      return new CMSMacCalculator(this.macOID, this.keySize, this.algorithmParameters, this.random);
   }

   private class CMSMacCalculator implements MacCalculator {
      private SecretKey encKey;
      private AlgorithmIdentifier algorithmIdentifier;
      private Mac mac;

      CMSMacCalculator(ASN1ObjectIdentifier var2, int var3, AlgorithmParameters var4, SecureRandom var5) throws CMSException {
         KeyGenerator var6 = JceCMSMacCalculatorBuilder.this.helper.createKeyGenerator(var2);
         if (var5 == null) {
            var5 = new SecureRandom();
         }

         if (var3 < 0) {
            var6.init(var5);
         } else {
            var6.init(var3, var5);
         }

         this.encKey = var6.generateKey();
         if (var4 == null) {
            var4 = JceCMSMacCalculatorBuilder.this.helper.generateParameters(var2, this.encKey, var5);
         }

         this.algorithmIdentifier = JceCMSMacCalculatorBuilder.this.helper.getAlgorithmIdentifier(var2, var4);
         this.mac = JceCMSMacCalculatorBuilder.this.helper.createContentMac(this.encKey, this.algorithmIdentifier);
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return this.algorithmIdentifier;
      }

      public OutputStream getOutputStream() {
         return new MacOutputStream(this.mac);
      }

      public byte[] getMac() {
         return this.mac.doFinal();
      }

      public GenericKey getKey() {
         return new JceGenericKey(this.algorithmIdentifier, this.encKey);
      }
   }
}
