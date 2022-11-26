package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.SignChallengeType;
import org.w3c.dom.Element;

public class SignChallengeTypeMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      SignChallengeType signChallengeType = (SignChallengeType)xmlObject;
      XMLObjectSupport.marshallAttributeMap(signChallengeType.getUnknownAttributes(), domElement);
   }
}
