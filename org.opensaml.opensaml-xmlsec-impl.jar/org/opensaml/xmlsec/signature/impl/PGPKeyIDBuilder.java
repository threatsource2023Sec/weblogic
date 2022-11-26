package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class PGPKeyIDBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public PGPKeyID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PGPKeyIDImpl(namespaceURI, localName, namespacePrefix);
   }

   public PGPKeyID buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID", "ds");
   }
}
