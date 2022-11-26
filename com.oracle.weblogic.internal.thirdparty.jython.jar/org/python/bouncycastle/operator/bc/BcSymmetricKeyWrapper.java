package org.python.bouncycastle.operator.bc;

import java.security.SecureRandom;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;
import org.python.bouncycastle.operator.SymmetricKeyWrapper;

public class BcSymmetricKeyWrapper extends SymmetricKeyWrapper {
   private SecureRandom random;
   private Wrapper wrapper;
   private KeyParameter wrappingKey;

   public BcSymmetricKeyWrapper(AlgorithmIdentifier var1, Wrapper var2, KeyParameter var3) {
      super(var1);
      this.wrapper = var2;
      this.wrappingKey = var3;
   }

   public BcSymmetricKeyWrapper setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public byte[] generateWrappedKey(GenericKey var1) throws OperatorException {
      byte[] var2 = OperatorUtils.getKeyBytes(var1);
      if (this.random == null) {
         this.wrapper.init(true, this.wrappingKey);
      } else {
         this.wrapper.init(true, new ParametersWithRandom(this.wrappingKey, this.random));
      }

      return this.wrapper.wrap(var2, 0, var2.length);
   }
}
