package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.DataReference;

public class DataReferenceImpl extends ReferenceTypeImpl implements DataReference {
   protected DataReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
