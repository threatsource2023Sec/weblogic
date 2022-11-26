package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Transforms;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class TransformsBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public Transforms buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TransformsImpl(namespaceURI, localName, namespacePrefix);
   }

   public Transforms buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transforms", "ds");
   }
}
