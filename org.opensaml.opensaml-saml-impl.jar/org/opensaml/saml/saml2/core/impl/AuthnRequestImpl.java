package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.Scoping;
import org.opensaml.saml.saml2.core.Subject;

public class AuthnRequestImpl extends RequestAbstractTypeImpl implements AuthnRequest {
   private Subject subject;
   private NameIDPolicy nameIDPolicy;
   private Conditions conditions;
   private RequestedAuthnContext requestedAuthnContext;
   private Scoping scoping;
   private XSBooleanValue forceAuthn;
   private XSBooleanValue isPassive;
   private String protocolBinding;
   private Integer assertionConsumerServiceIndex;
   private String assertionConsumerServiceURL;
   private Integer attributeConsumingServiceIndex;
   private String providerName;

   protected AuthnRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean isForceAuthn() {
      return this.forceAuthn != null ? this.forceAuthn.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isForceAuthnXSBoolean() {
      return this.forceAuthn;
   }

   public void setForceAuthn(Boolean newForceAuth) {
      if (newForceAuth != null) {
         this.forceAuthn = (XSBooleanValue)this.prepareForAssignment(this.forceAuthn, new XSBooleanValue(newForceAuth, false));
      } else {
         this.forceAuthn = (XSBooleanValue)this.prepareForAssignment(this.forceAuthn, (Object)null);
      }

   }

   public void setForceAuthn(XSBooleanValue newForceAuthn) {
      this.forceAuthn = (XSBooleanValue)this.prepareForAssignment(this.forceAuthn, newForceAuthn);
   }

   public Boolean isPassive() {
      return this.isPassive != null ? this.isPassive.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isPassiveXSBoolean() {
      return this.isPassive;
   }

   public void setIsPassive(Boolean newIsPassive) {
      if (newIsPassive != null) {
         this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, new XSBooleanValue(newIsPassive, false));
      } else {
         this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, (Object)null);
      }

   }

   public void setIsPassive(XSBooleanValue newIsPassive) {
      this.isPassive = (XSBooleanValue)this.prepareForAssignment(this.isPassive, newIsPassive);
   }

   public String getProtocolBinding() {
      return this.protocolBinding;
   }

   public void setProtocolBinding(String newProtocolBinding) {
      this.protocolBinding = this.prepareForAssignment(this.protocolBinding, newProtocolBinding);
   }

   public Integer getAssertionConsumerServiceIndex() {
      return this.assertionConsumerServiceIndex;
   }

   public void setAssertionConsumerServiceIndex(Integer newAssertionConsumerServiceIndex) {
      this.assertionConsumerServiceIndex = (Integer)this.prepareForAssignment(this.assertionConsumerServiceIndex, newAssertionConsumerServiceIndex);
   }

   public String getAssertionConsumerServiceURL() {
      return this.assertionConsumerServiceURL;
   }

   public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL) {
      this.assertionConsumerServiceURL = this.prepareForAssignment(this.assertionConsumerServiceURL, newAssertionConsumerServiceURL);
   }

   public Integer getAttributeConsumingServiceIndex() {
      return this.attributeConsumingServiceIndex;
   }

   public void setAttributeConsumingServiceIndex(Integer newAttributeConsumingServiceIndex) {
      this.attributeConsumingServiceIndex = (Integer)this.prepareForAssignment(this.attributeConsumingServiceIndex, newAttributeConsumingServiceIndex);
   }

   public String getProviderName() {
      return this.providerName;
   }

   public void setProviderName(String newProviderName) {
      this.providerName = this.prepareForAssignment(this.providerName, newProviderName);
   }

   public Subject getSubject() {
      return this.subject;
   }

   public void setSubject(Subject newSubject) {
      this.subject = (Subject)this.prepareForAssignment(this.subject, newSubject);
   }

   public NameIDPolicy getNameIDPolicy() {
      return this.nameIDPolicy;
   }

   public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
      this.nameIDPolicy = (NameIDPolicy)this.prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
   }

   public Conditions getConditions() {
      return this.conditions;
   }

   public void setConditions(Conditions newConditions) {
      this.conditions = (Conditions)this.prepareForAssignment(this.conditions, newConditions);
   }

   public RequestedAuthnContext getRequestedAuthnContext() {
      return this.requestedAuthnContext;
   }

   public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
      this.requestedAuthnContext = (RequestedAuthnContext)this.prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
   }

   public Scoping getScoping() {
      return this.scoping;
   }

   public void setScoping(Scoping newScoping) {
      this.scoping = (Scoping)this.prepareForAssignment(this.scoping, newScoping);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.subject != null) {
         children.add(this.subject);
      }

      if (this.nameIDPolicy != null) {
         children.add(this.nameIDPolicy);
      }

      if (this.conditions != null) {
         children.add(this.conditions);
      }

      if (this.requestedAuthnContext != null) {
         children.add(this.requestedAuthnContext);
      }

      if (this.scoping != null) {
         children.add(this.scoping);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
