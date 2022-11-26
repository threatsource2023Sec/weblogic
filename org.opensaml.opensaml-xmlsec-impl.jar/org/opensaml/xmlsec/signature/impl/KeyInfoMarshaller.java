package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Element;

public class KeyInfoMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      KeyInfo keyInfo = (KeyInfo)xmlObject;
      if (keyInfo.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", keyInfo.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

   }
}
