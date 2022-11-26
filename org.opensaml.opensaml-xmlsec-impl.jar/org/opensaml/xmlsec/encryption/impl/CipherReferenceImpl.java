package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.Transforms;

public class CipherReferenceImpl extends AbstractXMLObject implements CipherReference {
   private String uri;
   private Transforms transforms;

   protected CipherReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
   }

   public Transforms getTransforms() {
      return this.transforms;
   }

   public void setTransforms(Transforms newTransforms) {
      this.transforms = (Transforms)this.prepareForAssignment(this.transforms, newTransforms);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.transforms != null) {
         children.add(this.transforms);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
