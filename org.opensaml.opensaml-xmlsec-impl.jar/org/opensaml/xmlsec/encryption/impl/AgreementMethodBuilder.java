package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;

public class AgreementMethodBuilder extends AbstractXMLObjectBuilder implements XMLEncryptionBuilder {
   public AgreementMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AgreementMethodImpl(namespaceURI, localName, namespacePrefix);
   }

   public AgreementMethod buildObject() {
      return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
   }
}
