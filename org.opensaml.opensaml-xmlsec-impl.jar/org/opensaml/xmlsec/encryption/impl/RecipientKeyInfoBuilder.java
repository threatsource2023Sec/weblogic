package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class RecipientKeyInfoBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public RecipientKeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RecipientKeyInfoImpl(namespaceURI, localName, namespacePrefix);
   }

   public RecipientKeyInfo buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo", "xenc");
   }
}
