package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.SignChallenge;

public class SignChallengeBuilder extends AbstractWSTrustObjectBuilder {
   public SignChallenge buildObject() {
      return (SignChallenge)this.buildObject(SignChallenge.ELEMENT_NAME);
   }

   public SignChallenge buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignChallengeImpl(namespaceURI, localName, namespacePrefix);
   }
}
