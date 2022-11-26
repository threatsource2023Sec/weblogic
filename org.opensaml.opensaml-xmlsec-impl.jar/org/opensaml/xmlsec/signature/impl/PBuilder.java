package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public P buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PImpl(namespaceURI, localName, namespacePrefix);
   }

   public P buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "P", "ds");
   }
}
