package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Status;

public class ResponseImpl extends ResponseAbstractTypeImpl implements Response {
   private Status status;
   private final XMLObjectChildrenList assertions = new XMLObjectChildrenList(this);

   protected ResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAssertions() {
      return this.assertions;
   }

   public Status getStatus() {
      return this.status;
   }

   public void setStatus(Status s) {
      this.status = (Status)this.prepareForAssignment(this.status, s);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList(1 + this.assertions.size());
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.status != null) {
         children.add(this.status);
      }

      children.addAll(this.assertions);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
