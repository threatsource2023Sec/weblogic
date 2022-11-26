package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class CipherDataBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public CipherData buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CipherDataImpl(namespaceURI, localName, namespacePrefix);
   }

   public CipherData buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
   }
}
