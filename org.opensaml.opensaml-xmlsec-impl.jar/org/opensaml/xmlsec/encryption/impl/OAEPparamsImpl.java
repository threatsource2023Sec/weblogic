package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.encryption.OAEPparams;

public class OAEPparamsImpl extends XSBase64BinaryImpl implements OAEPparams {
   protected OAEPparamsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
