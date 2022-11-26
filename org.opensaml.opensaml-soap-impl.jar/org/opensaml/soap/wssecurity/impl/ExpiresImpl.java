package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Expires;

public class ExpiresImpl extends AttributedDateTimeImpl implements Expires {
   public ExpiresImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
