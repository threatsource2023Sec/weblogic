package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.w3c.dom.Element;

public class AgreementMethodMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AgreementMethod am = (AgreementMethod)xmlObject;
      if (am.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", am.getAlgorithm());
      }

   }
}
