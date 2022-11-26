package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.soap.wstrust.Participant;
import org.opensaml.soap.wstrust.Participants;
import org.opensaml.soap.wstrust.Primary;

public class ParticipantsUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      Participants participants = (Participants)parentXMLObject;
      if (childXMLObject instanceof Primary) {
         participants.setPrimary((Primary)childXMLObject);
      } else if (childXMLObject instanceof Participant) {
         participants.getParticipants().add((Participant)childXMLObject);
      } else {
         participants.getUnknownXMLObjects().add(childXMLObject);
      }

   }
}
