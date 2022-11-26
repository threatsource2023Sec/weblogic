package org.python.bouncycastle.crypto.tls;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.params.SRP6GroupParameters;

public class TlsSRPLoginParameters {
   protected SRP6GroupParameters group;
   protected BigInteger verifier;
   protected byte[] salt;

   public TlsSRPLoginParameters(SRP6GroupParameters var1, BigInteger var2, byte[] var3) {
      this.group = var1;
      this.verifier = var2;
      this.salt = var3;
   }

   public SRP6GroupParameters getGroup() {
      return this.group;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public BigInteger getVerifier() {
      return this.verifier;
   }
}
