package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.DigestMethod;
import org.w3c.dom.Element;

public class DigestMethodMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      DigestMethod dm = (DigestMethod)xmlObject;
      if (dm.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", dm.getAlgorithm());
      }

   }
}
