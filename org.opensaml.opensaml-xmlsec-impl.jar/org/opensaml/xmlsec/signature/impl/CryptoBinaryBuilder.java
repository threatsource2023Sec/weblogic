package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.xmlsec.signature.CryptoBinary;

public class CryptoBinaryBuilder extends AbstractXMLObjectBuilder implements XMLObjectBuilder {
   public CryptoBinary buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CryptoBinaryImpl(namespaceURI, localName, namespacePrefix);
   }
}
