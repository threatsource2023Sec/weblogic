package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.Transform;
import org.w3c.dom.Element;

public class TransformMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Transform transform = (Transform)xmlObject;
      if (transform.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", transform.getAlgorithm());
      }

   }
}
