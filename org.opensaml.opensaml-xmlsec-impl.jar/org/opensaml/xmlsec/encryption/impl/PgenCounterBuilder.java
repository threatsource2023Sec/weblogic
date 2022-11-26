package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class PgenCounterBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public PgenCounter buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
   }

   public PgenCounter buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "pgenCounter", "xenc");
   }
}
