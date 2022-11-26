package org.python.bouncycastle.crypto.parsers;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.crypto.KeyParser;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.util.io.Streams;

public class ECIESPublicKeyParser implements KeyParser {
   private ECDomainParameters ecParams;

   public ECIESPublicKeyParser(ECDomainParameters var1) {
      this.ecParams = var1;
   }

   public AsymmetricKeyParameter readKey(InputStream var1) throws IOException {
      int var2 = var1.read();
      byte[] var3;
      switch (var2) {
         case 0:
            throw new IOException("Sender's public key invalid.");
         case 1:
         case 5:
         default:
            throw new IOException("Sender's public key has invalid point encoding 0x" + Integer.toString(var2, 16));
         case 2:
         case 3:
            var3 = new byte[1 + (this.ecParams.getCurve().getFieldSize() + 7) / 8];
            break;
         case 4:
         case 6:
         case 7:
            var3 = new byte[1 + 2 * ((this.ecParams.getCurve().getFieldSize() + 7) / 8)];
      }

      var3[0] = (byte)var2;
      Streams.readFully(var1, var3, 1, var3.length - 1);
      return new ECPublicKeyParameters(this.ecParams.getCurve().decodePoint(var3), this.ecParams);
   }
}
