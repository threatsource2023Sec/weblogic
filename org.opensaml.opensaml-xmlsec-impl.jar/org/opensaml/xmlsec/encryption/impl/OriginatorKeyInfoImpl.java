package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.signature.impl.KeyInfoImpl;

public class OriginatorKeyInfoImpl extends KeyInfoImpl implements OriginatorKeyInfo {
   protected OriginatorKeyInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
