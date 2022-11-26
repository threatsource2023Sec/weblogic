package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.encryption.KANonce;

public class KANonceImpl extends XSBase64BinaryImpl implements KANonce {
   protected KANonceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
