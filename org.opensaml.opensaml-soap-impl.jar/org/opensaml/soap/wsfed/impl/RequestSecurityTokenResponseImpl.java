package org.opensaml.soap.wsfed.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.RequestSecurityTokenResponse;

public class RequestSecurityTokenResponseImpl extends AbstractXMLObject implements RequestSecurityTokenResponse {
   private final XMLObjectChildrenList requestedSecurityTokens = new XMLObjectChildrenList(this);
   private AppliesTo appliesTo;

   RequestSecurityTokenResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getRequestedSecurityToken() {
      return this.requestedSecurityTokens;
   }

   public AppliesTo getAppliesTo() {
      return this.appliesTo;
   }

   public void setAppliesTo(AppliesTo newappliesTo) {
      this.appliesTo = (AppliesTo)this.prepareForAssignment(this.appliesTo, newappliesTo);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList(1 + this.requestedSecurityTokens.size());
      children.addAll(this.requestedSecurityTokens);
      children.add(this.appliesTo);
      return Collections.unmodifiableList(children);
   }
}
