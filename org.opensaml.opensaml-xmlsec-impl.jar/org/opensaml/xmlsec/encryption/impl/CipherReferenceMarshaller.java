package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.w3c.dom.Element;

public class CipherReferenceMarshaller extends AbstractXMLEncryptionMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      CipherReference cr = (CipherReference)xmlObject;
      if (cr.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", cr.getURI());
      } else {
         super.marshallAttributes(xmlObject, domElement);
      }

   }
}
