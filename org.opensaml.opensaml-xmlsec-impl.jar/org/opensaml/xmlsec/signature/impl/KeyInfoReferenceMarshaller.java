package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.w3c.dom.Element;

public class KeyInfoReferenceMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      KeyInfoReference ref = (KeyInfoReference)xmlObject;
      if (ref.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", ref.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

      if (ref.getURI() != null) {
         domElement.setAttributeNS((String)null, "URI", ref.getURI());
      }

   }
}
