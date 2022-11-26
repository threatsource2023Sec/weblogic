package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.agreement.DHStandardGroups;
import org.python.bouncycastle.crypto.params.DHParameters;

public class PSKTlsServer extends AbstractTlsServer {
   protected TlsPSKIdentityManager pskIdentityManager;

   public PSKTlsServer(TlsPSKIdentityManager var1) {
      this(new DefaultTlsCipherFactory(), var1);
   }

   public PSKTlsServer(TlsCipherFactory var1, TlsPSKIdentityManager var2) {
      super(var1);
      this.pskIdentityManager = var2;
   }

   protected TlsEncryptionCredentials getRSAEncryptionCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected DHParameters getDHParameters() {
      return DHStandardGroups.rfc3526_2048;
   }

   protected int[] getCipherSuites() {
      return new int[]{49207, 49205, 178, 144};
   }

   public TlsCredentials getCredentials() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 13:
         case 14:
         case 24:
            return null;
         case 15:
            return this.getRSAEncryptionCredentials();
         default:
            throw new TlsFatalAlert((short)80);
      }
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

   protected TlsKeyExchange createPSKKeyExchange(int var1) {
      return new TlsPSKKeyExchange(var1, this.supportedSignatureAlgorithms, (TlsPSKIdentity)null, this.pskIdentityManager, this.getDHParameters(), this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
   }
}
