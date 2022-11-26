package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.ASN1Set;

interface AuthAttributesProvider {
   ASN1Set getAuthAttributes();
}
