package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PGPDataBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public PGPData buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PGPDataImpl(namespaceURI, localName, namespacePrefix);
   }

   public PGPData buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
   }
}
