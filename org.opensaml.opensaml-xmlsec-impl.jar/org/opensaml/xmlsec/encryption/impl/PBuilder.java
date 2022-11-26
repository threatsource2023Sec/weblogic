package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class PBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public P buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PImpl(namespaceURI, localName, namespacePrefix);
   }

   public P buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "P", "xenc");
   }
}
