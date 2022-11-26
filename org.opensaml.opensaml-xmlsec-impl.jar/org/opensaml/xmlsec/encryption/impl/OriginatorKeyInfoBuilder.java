package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class OriginatorKeyInfoBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public OriginatorKeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OriginatorKeyInfoImpl(namespaceURI, localName, namespacePrefix);
   }

   public OriginatorKeyInfo buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo", "xenc");
   }
}
