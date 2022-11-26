package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.SignChallengeResponse;

public class SignChallengeResponseBuilder extends AbstractWSTrustObjectBuilder {
   public SignChallengeResponse buildObject() {
      return (SignChallengeResponse)this.buildObject(SignChallengeResponse.ELEMENT_NAME);
   }

   public SignChallengeResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignChallengeResponseImpl(namespaceURI, localName, namespacePrefix);
   }
}
