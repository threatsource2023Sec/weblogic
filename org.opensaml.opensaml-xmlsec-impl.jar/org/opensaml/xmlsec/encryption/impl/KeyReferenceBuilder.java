package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class KeyReferenceBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public KeyReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyReferenceImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeyReference buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeyReference", "xenc");
   }
}
