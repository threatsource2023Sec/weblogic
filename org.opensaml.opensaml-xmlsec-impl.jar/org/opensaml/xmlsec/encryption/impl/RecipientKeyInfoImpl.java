package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.RecipientKeyInfo;
import org.opensaml.xmlsec.signature.impl.KeyInfoImpl;

public class RecipientKeyInfoImpl extends KeyInfoImpl implements RecipientKeyInfo {
   protected RecipientKeyInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
