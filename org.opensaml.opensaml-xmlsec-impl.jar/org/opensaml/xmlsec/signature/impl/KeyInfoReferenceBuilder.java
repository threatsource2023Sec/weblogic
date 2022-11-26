package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class KeyInfoReferenceBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public KeyInfoReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyInfoReferenceImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeyInfoReference buildObject() {
      return this.buildObject("http://www.w3.org/2009/xmldsig11#", "KeyInfoReference", "ds11");
   }
}
