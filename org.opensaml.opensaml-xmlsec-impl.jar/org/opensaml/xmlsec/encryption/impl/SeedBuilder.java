package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Seed;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class SeedBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public Seed buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SeedImpl(namespaceURI, localName, namespacePrefix);
   }

   public Seed buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "seed", "xenc");
   }
}
