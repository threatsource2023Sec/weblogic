package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;

public class RegistrationInfoImpl extends AbstractSAMLObject implements RegistrationInfo {
   private XMLObjectChildrenList registrationPolicies = new IndexedXMLObjectChildrenList(this);
   private String registrationAuthority;
   private DateTime registrationInstant;

   protected RegistrationInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getRegistrationAuthority() {
      return this.registrationAuthority;
   }

   public void setRegistrationAuthority(String authority) {
      this.registrationAuthority = this.prepareForAssignment(this.registrationAuthority, authority);
   }

   public DateTime getRegistrationInstant() {
      return this.registrationInstant;
   }

   public void setRegistrationInstant(DateTime dateTime) {
      this.registrationInstant = this.prepareForAssignment(this.registrationInstant, dateTime);
   }

   public List getRegistrationPolicies() {
      return this.registrationPolicies;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.registrationPolicies);
      return children;
   }
}
