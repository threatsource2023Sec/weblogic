package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.ProofEncryption;

public class ProofEncryptionBuilder extends AbstractWSTrustObjectBuilder {
   public ProofEncryption buildObject() {
      return (ProofEncryption)this.buildObject(ProofEncryption.ELEMENT_NAME);
   }

   public ProofEncryption buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ProofEncryptionImpl(namespaceURI, localName, namespacePrefix);
   }
}
