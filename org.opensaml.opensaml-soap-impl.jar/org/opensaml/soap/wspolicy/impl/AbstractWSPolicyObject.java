package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public abstract class AbstractWSPolicyObject extends AbstractXMLObject implements WSPolicyObject {
   public AbstractWSPolicyObject(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
