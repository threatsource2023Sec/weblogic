package org.opensaml.xacml.impl;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xacml.XACMLObject;

public abstract class AbstractXACMLObject extends AbstractXMLObject implements XACMLObject {
   protected AbstractXACMLObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
