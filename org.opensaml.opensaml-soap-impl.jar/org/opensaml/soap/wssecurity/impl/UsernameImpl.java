package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Username;

public class UsernameImpl extends AttributedStringImpl implements Username {
   public UsernameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
