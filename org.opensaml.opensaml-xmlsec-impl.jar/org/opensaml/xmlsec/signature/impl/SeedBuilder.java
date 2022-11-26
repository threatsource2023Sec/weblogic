package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Seed;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class SeedBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public Seed buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SeedImpl(namespaceURI, localName, namespacePrefix);
   }

   public Seed buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Seed", "ds");
   }
}
