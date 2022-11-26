package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.Transforms;

public class RetrievalMethodImpl extends AbstractXMLObject implements RetrievalMethod {
   private String uri;
   private String type;
   private Transforms transforms;

   protected RetrievalMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
   }

   public String getType() {
      return this.type;
   }

   public void setType(String newType) {
      this.type = this.prepareForAssignment(this.type, newType);
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
