package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public class X509IssuerSerialUnmarshaller extends AbstractXMLSignatureUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      X509IssuerSerial keyValue = (X509IssuerSerial)parentXMLObject;
      if (childXMLObject instanceof X509IssuerName) {
         keyValue.setX509IssuerName((X509IssuerName)childXMLObject);
      } else if (childXMLObject instanceof X509SerialNumber) {
         keyValue.setX509SerialNumber((X509SerialNumber)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
