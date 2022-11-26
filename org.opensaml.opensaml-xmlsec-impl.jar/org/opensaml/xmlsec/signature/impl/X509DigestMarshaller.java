package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.opensaml.xmlsec.signature.X509Digest;
import org.w3c.dom.Element;

public class X509DigestMarshaller extends XSBase64BinaryMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      X509Digest xd = (X509Digest)xmlObject;
      if (xd.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", xd.getAlgorithm());
      }

   }
}
