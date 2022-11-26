package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsAgreementCredentials extends TlsCredentials {
   byte[] generateAgreement(AsymmetricKeyParameter var1) throws IOException;
}
