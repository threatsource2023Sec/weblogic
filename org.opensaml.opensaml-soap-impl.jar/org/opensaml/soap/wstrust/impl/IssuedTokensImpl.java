package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.IssuedTokens;

public class IssuedTokensImpl extends RequestSecurityTokenResponseCollectionImpl implements IssuedTokens {
   public IssuedTokensImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
