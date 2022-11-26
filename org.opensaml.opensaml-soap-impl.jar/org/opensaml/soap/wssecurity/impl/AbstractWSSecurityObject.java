package org.opensaml.soap.wssecurity.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public abstract class AbstractWSSecurityObject extends AbstractXMLObject implements WSSecurityObject {
   public AbstractWSSecurityObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
