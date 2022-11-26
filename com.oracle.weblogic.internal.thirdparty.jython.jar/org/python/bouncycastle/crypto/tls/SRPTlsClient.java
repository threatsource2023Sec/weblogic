package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import org.python.bouncycastle.util.Arrays;

public class SRPTlsClient extends AbstractTlsClient {
   protected TlsSRPGroupVerifier groupVerifier;
   protected byte[] identity;
   protected byte[] password;

   public SRPTlsClient(byte[] var1, byte[] var2) {
      this(new DefaultTlsCipherFactory(), new DefaultTlsSRPGroupVerifier(), var1, var2);
   }

   public SRPTlsClient(TlsCipherFactory var1, byte[] var2, byte[] var3) {
      this(var1, new DefaultTlsSRPGroupVerifier(), var2, var3);
   }

   public SRPTlsClient(TlsCipherFactory var1, TlsSRPGroupVerifier var2, byte[] var3, byte[] var4) {
      super(var1);
      this.groupVerifier = var2;
      this.identity = Arrays.clone(var3);
      this.password = Arrays.clone(var4);
   }

   protected boolean requireSRPServerExtension() {
      return false;
   }

   public int[] getCipherSuites() {
      return new int[]{49182};
   }

   public Hashtable getClientExtensions() throws IOException {
      Hashtable var1 = TlsExtensionsUtils.ensureExtensionsInitialised(super.getClientExtensions());
      TlsSRPUtils.addSRPExtension(var1, this.identity);
      return var1;
   }

   public void processServerExtensions(Hashtable var1) throws IOException {
      if (!TlsUtils.hasExpectedEmptyExtensionData(var1, TlsSRPUtils.EXT_SRP, (short)47) && this.requireSRPServerExtension()) {
         throw new TlsFatalAlert((short)47);
      } else {
         super.processServerExtensions(var1);
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

   public TlsAuthentication getAuthentication() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsKeyExchange createSRPKeyExchange(int var1) {
      return new TlsSRPKeyExchange(var1, this.supportedSignatureAlgorithms, this.groupVerifier, this.identity, this.password);
   }
}
