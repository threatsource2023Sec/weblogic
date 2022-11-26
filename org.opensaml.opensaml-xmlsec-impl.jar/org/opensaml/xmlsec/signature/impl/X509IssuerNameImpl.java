package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.signature.X509IssuerName;

public class X509IssuerNameImpl extends XSStringImpl implements X509IssuerName {
   protected X509IssuerNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
