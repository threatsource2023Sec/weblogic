package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class KeyValueBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public KeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyValueImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeyValue buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyValue", "ds");
   }
}
