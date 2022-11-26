package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class DSAKeyValueBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public DSAKeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DSAKeyValueImpl(namespaceURI, localName, namespacePrefix);
   }

   public DSAKeyValue buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue", "ds");
   }
}
