package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.w3c.dom.Element;

public class ECKeyValueMarshaller extends AbstractXMLSignatureMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ECKeyValue ec = (ECKeyValue)xmlObject;
      if (ec.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", ec.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

   }
}
