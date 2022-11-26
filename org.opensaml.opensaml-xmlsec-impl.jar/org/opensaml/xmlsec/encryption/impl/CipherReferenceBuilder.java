package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class CipherReferenceBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public CipherReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CipherReferenceImpl(namespaceURI, localName, namespacePrefix);
   }

   public CipherReference buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherReference", "xenc");
   }
}
