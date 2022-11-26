package org.python.bouncycastle.crypto.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.python.bouncycastle.crypto.KeyParser;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.util.io.Streams;

public class DHIESPublicKeyParser implements KeyParser {
   private DHParameters dhParams;

   public DHIESPublicKeyParser(DHParameters var1) {
      this.dhParams = var1;
   }

   public AsymmetricKeyParameter readKey(InputStream var1) throws IOException {
      byte[] var2 = new byte[(this.dhParams.getP().bitLength() + 7) / 8];
      Streams.readFully(var1, var2, 0, var2.length);
      return new DHPublicKeyParameters(new BigInteger(1, var2), this.dhParams);
   }
}
