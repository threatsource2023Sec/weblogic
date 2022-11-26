package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.ParticipantType;

public class ParticipantTypeUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ParticipantType pt = (ParticipantType)parentXMLObject;
      pt.setUnknownXMLObject(childXMLObject);
   }
}
