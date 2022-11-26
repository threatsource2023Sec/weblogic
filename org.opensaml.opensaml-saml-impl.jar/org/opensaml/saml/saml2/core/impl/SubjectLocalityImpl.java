package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SubjectLocality;

public class SubjectLocalityImpl extends AbstractSAMLObject implements SubjectLocality {
   private String address;
   private String dnsName;

   protected SubjectLocalityImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String newAddress) {
      this.address = this.prepareForAssignment(this.address, newAddress);
   }

   public String getDNSName() {
      return this.dnsName;
   }

   public void setDNSName(String newDNSName) {
      this.dnsName = this.prepareForAssignment(this.dnsName, newDNSName);
   }

   public List getOrderedChildren() {
      return null;
   }
}
