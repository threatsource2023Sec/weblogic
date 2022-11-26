package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ReplyTo;

public class ReplyToImpl extends EndpointReferenceTypeImpl implements ReplyTo {
   public ReplyToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
