package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.RetryAfter;

public class RetryAfterImpl extends AttributedUnsignedLongImpl implements RetryAfter {
   public RetryAfterImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
