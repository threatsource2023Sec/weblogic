package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.signature.MgmtData;

public class MgmtDataImpl extends XSStringImpl implements MgmtData {
   protected MgmtDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
