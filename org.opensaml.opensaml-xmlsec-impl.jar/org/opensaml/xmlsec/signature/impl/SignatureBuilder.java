package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;

public class SignatureBuilder extends AbstractXMLObjectBuilder {
   public SignatureImpl buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
   }

   public SignatureImpl buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignatureImpl(namespaceURI, localName, namespacePrefix);
   }
}
