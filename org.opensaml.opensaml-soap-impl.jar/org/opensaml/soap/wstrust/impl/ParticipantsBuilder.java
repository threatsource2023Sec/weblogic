package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Participants;

public class ParticipantsBuilder extends AbstractWSTrustObjectBuilder {
   public Participants buildObject() {
      return (Participants)this.buildObject(Participants.ELEMENT_NAME);
   }

   public Participants buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ParticipantsImpl(namespaceURI, localName, namespacePrefix);
   }
}
