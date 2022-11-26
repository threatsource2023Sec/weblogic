package org.python.bouncycastle.cert.crmf;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface Control {
   ASN1ObjectIdentifier getType();

   ASN1Encodable getValue();
}
