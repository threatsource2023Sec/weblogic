package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.AuthenticationType;

public class AuthenticationTypeImpl extends XSURIImpl implements AuthenticationType {
   public AuthenticationTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
