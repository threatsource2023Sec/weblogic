package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class PublicBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public Public buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PublicImpl(namespaceURI, localName, namespacePrefix);
   }

   public Public buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Public", "xenc");
   }
}
