package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.Seed;

public class SeedImpl extends CryptoBinaryImpl implements Seed {
   protected SeedImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
