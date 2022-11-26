package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class EncryptedDataBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public EncryptedData buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptedData", "xenc");
   }

   public EncryptedData buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedDataImpl(namespaceURI, localName, namespacePrefix);
   }
}
