package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ReplyTo;

public class ReplyToBuilder extends AbstractWSAddressingObjectBuilder {
   public ReplyTo buildObject() {
      return (ReplyTo)this.buildObject(ReplyTo.ELEMENT_NAME);
   }

   public ReplyTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ReplyToImpl(namespaceURI, localName, namespacePrefix);
   }
}
