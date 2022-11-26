package org.python.bouncycastle.pkcs.jcajce;

import java.io.OutputStream;
import java.security.Provider;
import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
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
import org.python.bouncycastle.pkcs.PKCS12MacCalculatorBuilderProvider;

public class JcePKCS12MacCalculatorBuilderProvider implements PKCS12MacCalculatorBuilderProvider {
   private JcaJceHelper helper = new DefaultJcaJceHelper();

   public JcePKCS12MacCalculatorBuilderProvider setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcePKCS12MacCalculatorBuilderProvider setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public PKCS12MacCalculatorBuilder get(final AlgorithmIdentifier var1) {
      return new PKCS12MacCalculatorBuilder() {
         public MacCalculator build(char[] var1x) throws OperatorCreationException {
            final PKCS12PBEParams var2 = PKCS12PBEParams.getInstance(var1.getParameters());

            try {
               final ASN1ObjectIdentifier var3 = var1.getAlgorithm();
               final Mac var4 = JcePKCS12MacCalculatorBuilderProvider.this.helper.createMac(var3.getId());
               PBEParameterSpec var5 = new PBEParameterSpec(var2.getIV(), var2.getIterations().intValue());
               final PKCS12Key var6 = new PKCS12Key(var1x);
               var4.init(var6, var5);
               return new MacCalculator() {
                  public AlgorithmIdentifier getAlgorithmIdentifier() {
                     return new AlgorithmIdentifier(var3, var2);
                  }

                  public OutputStream getOutputStream() {
                     return new MacOutputStream(var4);
                  }

                  public byte[] getMac() {
                     return var4.doFinal();
                  }

                  public GenericKey getKey() {
                     return new GenericKey(this.getAlgorithmIdentifier(), var6.getEncoded());
                  }
               };
            } catch (Exception var7) {
               throw new OperatorCreationException("unable to create MAC calculator: " + var7.getMessage(), var7);
            }
         }

         public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
            return new AlgorithmIdentifier(var1.getAlgorithm(), DERNull.INSTANCE);
         }
      };
   }
}
