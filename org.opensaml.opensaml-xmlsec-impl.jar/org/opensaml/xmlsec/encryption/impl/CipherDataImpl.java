package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.CipherValue;

public class CipherDataImpl extends AbstractXMLObject implements CipherData {
   private CipherValue cipherValue;
   private CipherReference cipherReference;

   protected CipherDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public CipherValue getCipherValue() {
      return this.cipherValue;
   }

   public void setCipherValue(CipherValue newCipherValue) {
      this.cipherValue = (CipherValue)this.prepareForAssignment(this.cipherValue, newCipherValue);
   }

   public CipherReference getCipherReference() {
      return this.cipherReference;
   }

   public void setCipherReference(CipherReference newCipherReference) {
      this.cipherReference = (CipherReference)this.prepareForAssignment(this.cipherReference, newCipherReference);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.cipherValue != null) {
         children.add(this.cipherValue);
      }

      if (this.cipherReference != null) {
         children.add(this.cipherReference);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
