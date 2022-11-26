package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.params.DHParameters;

public class PSKTlsClient extends AbstractTlsClient {
   protected TlsPSKIdentity pskIdentity;

   public PSKTlsClient(TlsPSKIdentity var1) {
      this(new DefaultTlsCipherFactory(), var1);
   }

   public PSKTlsClient(TlsCipherFactory var1, TlsPSKIdentity var2) {
      super(var1);
      this.pskIdentity = var2;
   }

   public int[] getCipherSuites() {
      return new int[]{49207, 49205, 178, 144};
   }

   public TlsKeyExchange getKeyExchange() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 13:
         case 14:
         case 15:
         case 24:
            return this.createPSKKeyExchange(var1);
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   public TlsAuthentication getAuthentication() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsKeyExchange createPSKKeyExchange(int var1) {
      return new TlsPSKKeyExchange(var1, this.supportedSignatureAlgorithms, this.pskIdentity, (TlsPSKIdentityManager)null, (DHParameters)null, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
   }
}
