package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class CarriedKeyNameBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public CarriedKeyName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CarriedKeyNameImpl(namespaceURI, localName, namespacePrefix);
   }

   public CarriedKeyName buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName", "xenc");
   }
}
