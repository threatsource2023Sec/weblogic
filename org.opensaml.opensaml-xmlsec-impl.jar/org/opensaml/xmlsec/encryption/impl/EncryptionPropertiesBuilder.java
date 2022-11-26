package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class EncryptionPropertiesBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public EncryptionProperties buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionPropertiesImpl(namespaceURI, localName, namespacePrefix);
   }

   public EncryptionProperties buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
   }
}
