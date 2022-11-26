package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.MGF;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class MGFBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public MGF buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new MGFImpl(namespaceURI, localName, namespacePrefix);
   }

   public MGF buildObject() {
      return this.buildObject("http://www.w3.org/2009/xmlenc11#", "MGF", "xenc11");
   }
}
