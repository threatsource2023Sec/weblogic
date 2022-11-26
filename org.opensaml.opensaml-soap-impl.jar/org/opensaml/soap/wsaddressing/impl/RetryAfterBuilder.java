package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.RetryAfter;

public class RetryAfterBuilder extends AbstractWSAddressingObjectBuilder {
   public RetryAfter buildObject() {
      return (RetryAfter)this.buildObject(RetryAfter.ELEMENT_NAME);
   }

   public RetryAfter buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RetryAfterImpl(namespaceURI, localName, namespacePrefix);
   }
}
