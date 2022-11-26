package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PublicKey;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PublicKeyBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public PublicKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PublicKeyImpl(namespaceURI, localName, namespacePrefix);
   }

   public PublicKey buildObject() {
      return this.buildObject("http://www.w3.org/2009/xmldsig11#", "PublicKey", "ds11");
   }
}
