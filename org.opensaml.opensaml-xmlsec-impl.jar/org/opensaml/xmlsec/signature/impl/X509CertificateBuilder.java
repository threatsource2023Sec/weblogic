package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509CertificateBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509Certificate buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509CertificateImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509Certificate buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Certificate", "ds");
   }
}
