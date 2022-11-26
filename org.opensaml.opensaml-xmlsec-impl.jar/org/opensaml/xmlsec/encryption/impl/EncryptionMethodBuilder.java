package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class EncryptionMethodBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public EncryptionMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionMethodImpl(namespaceURI, localName, namespacePrefix);
   }

   public EncryptionMethod buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
   }
}
