package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface CMSTypedData extends CMSProcessable {
   ASN1ObjectIdentifier getContentType();
}
