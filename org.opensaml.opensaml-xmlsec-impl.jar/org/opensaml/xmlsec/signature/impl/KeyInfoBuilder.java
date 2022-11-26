package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class KeyInfoBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public KeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyInfoImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeyInfo buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyInfo", "ds");
   }
}
