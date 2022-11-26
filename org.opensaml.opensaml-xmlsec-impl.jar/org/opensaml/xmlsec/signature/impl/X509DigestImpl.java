package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.signature.X509Digest;

public class X509DigestImpl extends XSBase64BinaryImpl implements X509Digest {
   private String algorithm;

   protected X509DigestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }
}
