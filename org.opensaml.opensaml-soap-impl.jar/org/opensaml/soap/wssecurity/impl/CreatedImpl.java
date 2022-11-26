package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Created;

public class CreatedImpl extends AttributedDateTimeImpl implements Created {
   public CreatedImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
