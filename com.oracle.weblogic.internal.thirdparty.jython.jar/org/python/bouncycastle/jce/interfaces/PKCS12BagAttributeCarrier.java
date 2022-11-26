package org.python.bouncycastle.jce.interfaces;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface PKCS12BagAttributeCarrier {
   void setBagAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2);

   ASN1Encodable getBagAttribute(ASN1ObjectIdentifier var1);

   Enumeration getBagAttributeKeys();
}
