package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class KeySizeBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public KeySize buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeySizeImpl(namespaceURI, localName, namespacePrefix);
   }

   public KeySize buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeySize", "xenc");
   }
}
