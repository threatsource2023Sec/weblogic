package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.ext.samlec.EncType;

public class EncTypeImpl extends XSStringImpl implements EncType {
   protected EncTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
