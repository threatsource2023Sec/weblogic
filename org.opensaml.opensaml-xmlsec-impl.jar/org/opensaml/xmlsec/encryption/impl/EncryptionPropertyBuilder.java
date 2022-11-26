package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionProperty;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class EncryptionPropertyBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public EncryptionProperty buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptionPropertyImpl(namespaceURI, localName, namespacePrefix);
   }

   public EncryptionProperty buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
   }
}
