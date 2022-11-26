package org.python.bouncycastle.cms;

import java.io.InputStream;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.util.io.TeeInputStream;

public class RecipientOperator {
   private final AlgorithmIdentifier algorithmIdentifier;
   private final Object operator;

   public RecipientOperator(InputDecryptor var1) {
      this.algorithmIdentifier = var1.getAlgorithmIdentifier();
      this.operator = var1;
   }

   public RecipientOperator(MacCalculator var1) {
      this.algorithmIdentifier = var1.getAlgorithmIdentifier();
      this.operator = var1;
   }

   public InputStream getInputStream(InputStream var1) {
      return (InputStream)(this.operator instanceof InputDecryptor ? ((InputDecryptor)this.operator).getInputStream(var1) : new TeeInputStream(var1, ((MacCalculator)this.operator).getOutputStream()));
   }

   public boolean isMacBased() {
      return this.operator instanceof MacCalculator;
   }

   public byte[] getMac() {
      return ((MacCalculator)this.operator).getMac();
   }
}
