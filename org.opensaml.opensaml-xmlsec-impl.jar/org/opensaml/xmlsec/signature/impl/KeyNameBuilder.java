package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class KeyNameBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public KeyName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyNameImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeyName buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyName", "ds");
   }
}
