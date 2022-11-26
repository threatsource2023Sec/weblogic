package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.EncryptedData;

public class EncryptedDataImpl extends EncryptedTypeImpl implements EncryptedData {
   protected EncryptedDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
