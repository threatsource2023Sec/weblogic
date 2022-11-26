package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class KANonceBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public KANonce buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KANonceImpl(namespaceURI, localName, namespacePrefix);
   }

   public KANonce buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KA-Nonce", "xenc");
   }
}
