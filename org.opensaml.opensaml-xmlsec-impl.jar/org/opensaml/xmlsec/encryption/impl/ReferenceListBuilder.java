package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class ReferenceListBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public ReferenceList buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ReferenceListImpl(namespaceURI, localName, namespacePrefix);
   }

   public ReferenceList buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");
   }
}
