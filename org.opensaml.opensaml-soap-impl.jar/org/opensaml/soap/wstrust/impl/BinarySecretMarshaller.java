package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.BinarySecret;
import org.w3c.dom.Element;

public class BinarySecretMarshaller extends XSBase64BinaryMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      BinarySecret bs = (BinarySecret)xmlObject;
      if (bs.getType() != null) {
         domElement.setAttributeNS((String)null, "Type", bs.getType());
      }

      XMLObjectSupport.marshallAttributeMap(bs.getUnknownAttributes(), domElement);
   }
}
