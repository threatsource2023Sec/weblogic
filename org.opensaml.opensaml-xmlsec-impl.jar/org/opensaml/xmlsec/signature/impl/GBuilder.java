package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class GBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public G buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GImpl(namespaceURI, localName, namespacePrefix);
   }

   public G buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "G", "ds");
   }
}
