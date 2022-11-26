package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.Entropy;
import org.w3c.dom.Element;

public class EntropyMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Entropy entropy = (Entropy)xmlObject;
      XMLObjectSupport.marshallAttributeMap(entropy.getUnknownAttributes(), domElement);
   }
}
