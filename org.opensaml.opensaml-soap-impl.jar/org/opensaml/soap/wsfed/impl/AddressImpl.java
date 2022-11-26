package org.opensaml.soap.wsfed.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.soap.wsfed.Address;

public class AddressImpl extends AbstractXMLObject implements Address {
   private String value;

   protected AddressImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return new ArrayList();
   }
}
