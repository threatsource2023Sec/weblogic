package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;

public class RequestedAuthnContextImpl extends AbstractSAMLObject implements RequestedAuthnContext {
   private final XMLObjectChildrenList authnContextClassRefs = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList authnContextDeclRefs = new XMLObjectChildrenList(this);
   private AuthnContextComparisonTypeEnumeration comparison;

   protected RequestedAuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AuthnContextComparisonTypeEnumeration getComparison() {
      return this.comparison;
   }

   public void setComparison(AuthnContextComparisonTypeEnumeration newComparison) {
      this.comparison = (AuthnContextComparisonTypeEnumeration)this.prepareForAssignment(this.comparison, newComparison);
   }

   public List getAuthnContextClassRefs() {
      return this.authnContextClassRefs;
   }

   public List getAuthnContextDeclRefs() {
      return this.authnContextDeclRefs;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.authnContextClassRefs);
      children.addAll(this.authnContextDeclRefs);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
