package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.signature.X509SubjectName;

public class X509SubjectNameImpl extends XSStringImpl implements X509SubjectName {
   protected X509SubjectNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
