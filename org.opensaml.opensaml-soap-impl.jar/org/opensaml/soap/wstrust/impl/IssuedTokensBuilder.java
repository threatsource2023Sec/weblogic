package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.IssuedTokens;

public class IssuedTokensBuilder extends AbstractWSTrustObjectBuilder {
   public IssuedTokens buildObject() {
      return (IssuedTokens)this.buildObject(IssuedTokens.ELEMENT_NAME);
   }

   public IssuedTokens buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IssuedTokensImpl(namespaceURI, localName, namespacePrefix);
   }
}
