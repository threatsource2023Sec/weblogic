package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class QBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public Q buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new QImpl(namespaceURI, localName, namespacePrefix);
   }

   public Q buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Q", "xenc");
   }
}
