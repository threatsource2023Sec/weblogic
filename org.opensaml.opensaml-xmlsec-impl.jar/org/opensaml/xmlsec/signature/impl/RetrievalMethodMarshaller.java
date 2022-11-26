package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.w3c.dom.Element;

public class RetrievalMethodMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RetrievalMethod rm = (RetrievalMethod)xmlObject;
      if (rm.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", rm.getURI());
      }

      if (rm.getType() != null) {
         domElement.setAttributeNS((String)null, "Type", rm.getType());
      }

   }
}
