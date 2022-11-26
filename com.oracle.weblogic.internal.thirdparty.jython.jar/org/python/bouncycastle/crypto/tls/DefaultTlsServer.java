package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.agreement.DHStandardGroups;
import org.python.bouncycastle.crypto.params.DHParameters;

public abstract class DefaultTlsServer extends AbstractTlsServer {
   public DefaultTlsServer() {
   }

   public DefaultTlsServer(TlsCipherFactory var1) {
      super(var1);
   }

   protected TlsSignerCredentials getDSASignerCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsSignerCredentials getECDSASignerCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsEncryptionCredentials getRSAEncryptionCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected TlsSignerCredentials getRSASignerCredentials() throws IOException {
      throw new TlsFatalAlert((short)80);
   }

   protected DHParameters getDHParameters() {
      return DHStandardGroups.rfc3526_2048;
   }

   protected int[] getCipherSuites() {
      return new int[]{49200, 49199, 49192, 49191, 49172, 49171, 159, 158, 107, 103, 57, 51, 157, 156, 61, 60, 53, 47};
   }

   public TlsCredentials getCredentials() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 1:
            return this.getRSAEncryptionCredentials();
         case 2:
         case 4:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 18:
         default:
            throw new TlsFatalAlert((short)80);
         case 3:
            return this.getDSASignerCredentials();
         case 5:
         case 19:
            return this.getRSASignerCredentials();
         case 11:
         case 20:
            return null;
         case 17:
            return this.getECDSASignerCredentials();
      }
   }

   public TlsKeyExchange getKeyExchange() throws IOException {
      int var1 = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
      switch (var1) {
         case 1:
            return this.createRSAKeyExchange();
         case 2:
         case 4:
         case 6:
         case 8:
         case 10:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            throw new TlsFatalAlert((short)80);
         case 3:
         case 5:
            return this.createDHEKeyExchange(var1);
         case 7:
         case 9:
         case 11:
            return this.createDHKeyExchange(var1);
         case 16:
         case 18:
         case 20:
            return this.createECDHKeyExchange(var1);
         case 17:
         case 19:
            return this.createECDHEKeyExchange(var1);
      }
   }

   protected TlsKeyExchange createDHKeyExchange(int var1) {
      return new TlsDHKeyExchange(var1, this.supportedSignatureAlgorithms, this.getDHParameters());
   }

   protected TlsKeyExchange createDHEKeyExchange(int var1) {
      return new TlsDHEKeyExchange(var1, this.supportedSignatureAlgorithms, this.getDHParameters());
   }

   protected TlsKeyExchange createECDHKeyExchange(int var1) {
      return new TlsECDHKeyExchange(var1, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
   }

   protected TlsKeyExchange createECDHEKeyExchange(int var1) {
      return new TlsECDHEKeyExchange(var1, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
   }

   protected TlsKeyExchange createRSAKeyExchange() {
      return new TlsRSAKeyExchange(this.supportedSignatureAlgorithms);
   }
}
