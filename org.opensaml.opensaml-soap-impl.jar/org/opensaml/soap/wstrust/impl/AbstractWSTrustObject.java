package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public abstract class AbstractWSTrustObject extends AbstractXMLObject implements WSTrustObject {
   public AbstractWSTrustObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
