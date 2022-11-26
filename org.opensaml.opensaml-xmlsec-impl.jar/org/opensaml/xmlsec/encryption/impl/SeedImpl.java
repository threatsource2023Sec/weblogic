package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.Seed;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class SeedImpl extends CryptoBinaryImpl implements Seed {
   protected SeedImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
