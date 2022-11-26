package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.TokenType;

public class TokenTypeBuilder extends AbstractWSTrustObjectBuilder {
   public TokenType buildObject() {
      return (TokenType)this.buildObject(TokenType.ELEMENT_NAME);
   }

   public TokenType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TokenTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
