package org.opensaml.saml.ext.saml1md.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.saml.ext.saml1md.SourceID;

public class SourceIDImpl extends XSStringImpl implements SourceID {
   protected SourceIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
