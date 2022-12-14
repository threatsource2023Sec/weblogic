package org.python.bouncycastle.asn1;

import java.io.InputStream;

public interface ASN1OctetStringParser extends ASN1Encodable, InMemoryRepresentable {
   InputStream getOctetStream();
}
