package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509SubjectName;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;

public class X509SubjectNameBuilder extends AbstractXMLObjectBuilder implements XMLSignatureBuilder {
   public X509SubjectName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new X509SubjectNameImpl(namespaceURI, localName, namespacePrefix);
   }

   public X509SubjectName buildObject() {
      return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName", "ds");
   }
}
