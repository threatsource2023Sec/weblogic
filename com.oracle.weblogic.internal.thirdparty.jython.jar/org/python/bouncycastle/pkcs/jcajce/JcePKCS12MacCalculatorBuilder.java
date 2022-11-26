package org.python.bouncycastle.pkcs.jcajce;

import java.io.OutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.PKCS12Key;
import org.python.bouncycastle.jcajce.io.MacOutputStream;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.pkcs.PKCS12MacCalculatorBuilder;

public class JcePKCS12MacCalculatorBuilder implements PKCS12MacCalculatorBuilder {
   private JcaJceHelper helper;
   private ASN1ObjectIdentifier algorithm;
   private SecureRandom random;
   private int saltLength;
   private int iterationCount;

   public JcePKCS12MacCalculatorBuilder() {
      this(OIWObjectIdentifiers.idSHA1);
   }

   public JcePKCS12MacCalculatorBuilder(ASN1ObjectIdentifier var1) {
      this.helper = new DefaultJcaJceHelper();
      this.iterationCount = 1024;
      this.algorithm = var1;
   }

   public JcePKCS12MacCalculatorBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcePKCS12MacCalculatorBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public JcePKCS12MacCalculatorBuilder setIterationCount(int var1) {
      this.iterationCount = var1;
      return this;
   }

   public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
      return new AlgorithmIdentifier(this.algorithm, DERNull.INSTANCE);
   }

   public MacCalculator build(char[] var1) throws OperatorCreationException {
      if (this.random == null) {
         this.random = new SecureRandom();
      }

      try {
         final Mac var2 = this.helper.createMac(this.algorithm.getId());
         this.saltLength = var2.getMacLength();
         final byte[] var3 = new byte[this.saltLength];
         this.random.nextBytes(var3);
         PBEParameterSpec var4 = new PBEParameterSpec(var3, this.iterationCount);
         final PKCS12Key var5 = new PKCS12Key(var1);
         var2.init(var5, var4);
         return new MacCalculator() {
            public AlgorithmIdentifier getAlgorithmIdentifier() {
               return new AlgorithmIdentifier(JcePKCS12MacCalculatorBuilder.this.algorithm, new PKCS12PBEParams(var3, JcePKCS12MacCalculatorBuilder.this.iterationCount));
            }

            public OutputStream getOutputStream() {
               return new MacOutputStream(var2);
            }

            public byte[] getMac() {
               return var2.doFinal();
            }

            public GenericKey getKey() {
               return new GenericKey(this.getAlgorithmIdentifier(), var5.getEncoded());
            }
         };
      } catch (Exception var6) {
         throw new OperatorCreationException("unable to create MAC calculator: " + var6.getMessage(), var6);
      }
   }
}
