package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.AppliesTo;
import org.w3c.dom.Element;

public class AppliesToMarshaller extends AbstractWSPolicyObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AppliesTo at = (AppliesTo)xmlObject;
      XMLObjectSupport.marshallAttributeMap(at.getUnknownAttributes(), domElement);
   }
}
