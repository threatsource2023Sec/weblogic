package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryUnmarshaller;
import org.opensaml.xmlsec.signature.X509Digest;
import org.w3c.dom.Attr;

public class X509DigestUnmarshaller extends XSBase64BinaryUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      X509Digest xd = (X509Digest)xmlObject;
      if (attribute.getLocalName().equals("Algorithm")) {
         xd.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
