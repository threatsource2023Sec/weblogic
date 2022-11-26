package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.SubjectLocality;

public class SubjectLocalityImpl extends AbstractSAMLObject implements SubjectLocality {
   private String ipAddress;
   private String dnsAddress;

   protected SubjectLocalityImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getIPAddress() {
      return this.ipAddress;
   }

   public void setIPAddress(String address) {
      this.ipAddress = this.prepareForAssignment(this.ipAddress, address);
   }

   public String getDNSAddress() {
      return this.dnsAddress;
   }

   public void setDNSAddress(String address) {
      this.dnsAddress = this.prepareForAssignment(this.dnsAddress, address);
   }

   public List getOrderedChildren() {
      return null;
   }
}
