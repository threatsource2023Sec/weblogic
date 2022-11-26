package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.encryption.CipherValue;

public class CipherValueImpl extends XSBase64BinaryImpl implements CipherValue {
   protected CipherValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
