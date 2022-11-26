package org.python.bouncycastle.crypto.tls;

public abstract class ServerOnlyTlsAuthentication implements TlsAuthentication {
   public final TlsCredentials getClientCredentials(CertificateRequest var1) {
      return null;
   }
}
