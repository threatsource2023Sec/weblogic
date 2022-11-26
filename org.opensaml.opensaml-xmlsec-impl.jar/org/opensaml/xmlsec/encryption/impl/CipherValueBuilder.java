package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherValue;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class CipherValueBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public CipherValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CipherValueImpl(namespaceURI, localName, namespacePrefix);
   }

   public CipherValue buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherValue", "xenc");
   }
}
