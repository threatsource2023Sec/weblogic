package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.Challenge;
import org.opensaml.soap.wstrust.SignChallengeType;
import org.w3c.dom.Attr;

public class SignChallengeTypeUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      SignChallengeType signChallengeType = (SignChallengeType)xmlObject;
      XMLObjectSupport.unmarshallToAttributeMap(signChallengeType.getUnknownAttributes(), attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      SignChallengeType signChallengeType = (SignChallengeType)parentXMLObject;
      if (childXMLObject instanceof Challenge) {
         signChallengeType.setChallenge((Challenge)childXMLObject);
      } else {
         signChallengeType.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
