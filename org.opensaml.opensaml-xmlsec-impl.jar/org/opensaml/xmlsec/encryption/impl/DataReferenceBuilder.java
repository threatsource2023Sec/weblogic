package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class DataReferenceBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public DataReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DataReferenceImpl(namespaceURI, localName, namespacePrefix);
   }

   public DataReference buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DataReference", "xenc");
   }
}
