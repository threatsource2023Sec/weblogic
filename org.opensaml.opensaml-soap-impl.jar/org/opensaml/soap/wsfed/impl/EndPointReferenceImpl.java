package org.opensaml.soap.wsfed.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wsfed.Address;
import org.opensaml.soap.wsfed.EndPointReference;

public class EndPointReferenceImpl extends AbstractXMLObject implements EndPointReference {
   private Address address;

   protected EndPointReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Address getAddress() {
      return this.address;
   }

   public void setAddress(Address newAddress) {
      this.address = (Address)this.prepareForAssignment(this.address, newAddress);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.address);
      return Collections.unmodifiableList(children);
   }
}
