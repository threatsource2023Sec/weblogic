package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.DHKeyValue;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class DHKeyValueBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public DHKeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DHKeyValueImpl(namespaceURI, localName, namespacePrefix);
   }

   public DHKeyValue buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DHKeyValue", "xenc");
   }
}
