package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import org.python.bouncycastle.crypto.Signer;

class SignerInputBuffer extends ByteArrayOutputStream {
   void updateSigner(Signer var1) {
      var1.update(this.buf, 0, this.count);
   }
}
