package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;
import org.opensaml.saml.saml2.core.Evidence;

public class AuthzDecisionQueryImpl extends SubjectQueryImpl implements AuthzDecisionQuery {
   private String resource;
   private Evidence evidence;
   private final XMLObjectChildrenList actions = new XMLObjectChildrenList(this);

   protected AuthzDecisionQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String newResource) {
      this.resource = this.prepareForAssignment(this.resource, newResource);
   }

   public List getActions() {
      return this.actions;
   }

   public Evidence getEvidence() {
      return this.evidence;
   }

   public void setEvidence(Evidence newEvidence) {
      this.evidence = (Evidence)this.prepareForAssignment(this.evidence, newEvidence);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      children.addAll(this.actions);
      if (this.evidence != null) {
         children.add(this.evidence);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
