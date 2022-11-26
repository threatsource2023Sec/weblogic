package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.Y;

public class YBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public Y buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new YImpl(namespaceURI, localName, namespacePrefix);
   }

   public Y buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Y", "ds");
   }
}
