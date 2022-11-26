package org.opensaml.soap.wsaddressing.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public abstract class AbstractWSAddressingObject extends AbstractXMLObject implements WSAddressingObject {
   public AbstractWSAddressingObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
