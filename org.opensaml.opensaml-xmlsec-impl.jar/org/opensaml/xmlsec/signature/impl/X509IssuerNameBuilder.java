package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509IssuerNameBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509IssuerName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509IssuerNameImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509IssuerName buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName", "ds");
   }
}
