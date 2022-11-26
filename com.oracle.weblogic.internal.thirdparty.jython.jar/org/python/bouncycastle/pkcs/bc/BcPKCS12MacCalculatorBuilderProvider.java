package org.python.bouncycastle.pkcs.bc;

import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.bc.BcDigestProvider;
import org.python.bouncycastle.pkcs.PKCS12MacCalculatorBuilder;
import org.python.bouncycastle.pkcs.PKCS12MacCalculatorBuilderProvider;

public class BcPKCS12MacCalculatorBuilderProvider implements PKCS12MacCalculatorBuilderProvider {
   private BcDigestProvider digestProvider;

   public BcPKCS12MacCalculatorBuilderProvider(BcDigestProvider var1) {
      this.digestProvider = var1;
   }

   public PKCS12MacCalculatorBuilder get(final AlgorithmIdentifier var1) {
      return new PKCS12MacCalculatorBuilder() {
         public MacCalculator build(char[] var1x) throws OperatorCreationException {
            PKCS12PBEParams var2 = PKCS12PBEParams.getInstance(var1.getParameters());
            return PKCS12PBEUtils.createMacCalculator(var1.getAlgorithm(), BcPKCS12MacCalculatorBuilderProvider.this.digestProvider.get(var1), var2, var1x);
         }

         public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
            return new AlgorithmIdentifier(var1.getAlgorithm(), DERNull.INSTANCE);
         }
      };
   }
}
