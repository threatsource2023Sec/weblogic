package org.opensaml.xmlsec.signature.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.X509SerialNumber;
import org.w3c.dom.Element;

public class X509SerialNumberMarshaller extends AbstractXMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      X509SerialNumber x509SerialNumber = (X509SerialNumber)xmlObject;
      if (x509SerialNumber.getValue() != null) {
         ElementSupport.appendTextContent(domElement, x509SerialNumber.getValue().toString());
      }

   }
}
