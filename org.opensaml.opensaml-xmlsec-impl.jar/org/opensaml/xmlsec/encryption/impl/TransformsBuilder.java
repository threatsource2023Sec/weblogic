package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Transforms;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class TransformsBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public Transforms buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TransformsImpl(namespaceURI, localName, namespacePrefix);
   }

   public Transforms buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
   }
}
