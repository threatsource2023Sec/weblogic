package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.w3c.dom.Element;

public class DEREncodedKeyValueMarshaller extends XSBase64BinaryMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      DEREncodedKeyValue der = (DEREncodedKeyValue)xmlObject;
      if (der.getID() != null) {
         domElement.setAttributeNS((String)null, "Id", der.getID());
         domElement.setIdAttributeNS((String)null, "Id", true);
      }

   }
}
