package org.python.bouncycastle.jcajce.provider.util;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public interface AsymmetricKeyInfoConverter {
   PrivateKey generatePrivate(PrivateKeyInfo var1) throws IOException;

   PublicKey generatePublic(SubjectPublicKeyInfo var1) throws IOException;
}
