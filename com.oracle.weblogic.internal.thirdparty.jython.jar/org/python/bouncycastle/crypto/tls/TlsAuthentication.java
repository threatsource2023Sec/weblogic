package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public interface TlsAuthentication {
   void notifyServerCertificate(Certificate var1) throws IOException;

   TlsCredentials getClientCredentials(CertificateRequest var1) throws IOException;
}
