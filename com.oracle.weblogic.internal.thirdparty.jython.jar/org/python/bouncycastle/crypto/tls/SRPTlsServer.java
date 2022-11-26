package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;

public class SRPTlsServer extends AbstractTlsServer {
   protected TlsSRPIdentityManager srpIdentityManager;
   protected byte[] srpIdentity;
   protected TlsSRPLoginParameters loginParameters;

   public SRPTlsServer(TlsSRPIdentityManager var1) {
      this(new DefaultTlsCipherFactory(), var1);
   }

   public SRPTlsServer(TlsCipherFactory var1, TlsSRPIdentityManager var2) {
      super(var1);
      this.srpIdentity = null;
      this.loginParameters = null;
      this.srpIdentityManager = var2;
   }

   protected TlsSignerCredentials getDSASignerCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsSignerCredentials getRSASignerCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected int[] getCipherSuites() {
      return new int[]{49186, 49183, 49185, 49182, 49184, 49181};
   }

   public void processClientExtensions(Hashtable var1) throws IOException {
      super.processClientExtensions(var1);
      this.srpIdentity = TlsSRPUtils.getSRPExtension(var1);
   }

   public int getSelectedCipherSuite() throws IOException {
      int var1 = super.getSelectedCipherSuite();
      if (TlsSRPUtils.isSRPCipherSuite(var1)) {
         if (this.srpIdentity != null) {
            this.loginParameters = this.srpIdentityManager.getLoginParameters(this.srpIdentity);
         }

         if (this.loginParameters == null) {
            throw new TlsFatalAlert((short)115);
         }
      }

      return var1;
   }

   public TlsCredentials getCredentials() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 21:
            return null;
         case 22:
            return this.getDSASignerCredentials();
         case 23:
            return this.getRSASignerCredentials();
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   public TlsKeyExchange getKeyExchange() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 21:
         case 22:
         case 23:
            return this.createSRPKeyExchange(var1);
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   protected TlsKeyExchange createSRPKeyExchange(int var1) {
      return new TlsSRPKeyExchange(var1, this.supportedSignatureAlgorithms, this.srpIdentity, this.loginParameters);
   }
}
