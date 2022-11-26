package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Participant;

public class ParticipantBuilder extends AbstractWSTrustObjectBuilder {
   public Participant buildObject() {
      return (Participant)this.buildObject(Participant.ELEMENT_NAME);
   }

   public Participant buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ParticipantImpl(namespaceURI, localName, namespacePrefix);
   }
}
