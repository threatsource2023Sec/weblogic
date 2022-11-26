package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class JBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public J buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new JImpl(namespaceURI, localName, namespacePrefix);
   }

   public J buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "J", "ds");
   }
}
