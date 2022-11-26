package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class EncryptedKeyBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public EncryptedKey buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
   }

   public EncryptedKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedKeyImpl(namespaceURI, localName, namespacePrefix);
   }
}
