package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class GeneratorBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public Generator buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GeneratorImpl(namespaceURI, localName, namespacePrefix);
   }

   public Generator buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Generator", "xenc");
   }
}
