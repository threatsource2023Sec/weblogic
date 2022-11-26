package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Challenge;

public class ChallengeBuilder extends AbstractWSTrustObjectBuilder {
   public Challenge buildObject() {
      return (Challenge)this.buildObject(Challenge.ELEMENT_NAME);
   }

   public Challenge buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ChallengeImpl(namespaceURI, localName, namespacePrefix);
   }
}
