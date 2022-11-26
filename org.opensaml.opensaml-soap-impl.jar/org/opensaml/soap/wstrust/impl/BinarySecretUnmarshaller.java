package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.BinarySecret;
import org.w3c.dom.Attr;

public class BinarySecretUnmarshaller extends XSBase64BinaryUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      BinarySecret bs = (BinarySecret)xmlObject;
      if ("Type".equals(attribute.getLocalName())) {
         bs.setType(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(bs.getUnknownAttributes(), attribute);
      }

   }
}
