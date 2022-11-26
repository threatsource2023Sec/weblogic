package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wstrust.Forwardable;

public class ForwardableImpl extends AbstractWSTrustObject implements Forwardable {
   private static final Boolean DEFAULT_VALUE;
   private XSBooleanValue value;

   public ForwardableImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.value = new XSBooleanValue(DEFAULT_VALUE, false);
   }

   public XSBooleanValue getValue() {
      return this.value;
   }

   public void setValue(XSBooleanValue newValue) {
      if (newValue != null) {
         this.value = (XSBooleanValue)this.prepareForAssignment(this.value, newValue);
      } else {
         this.value = (XSBooleanValue)this.prepareForAssignment(this.value, new XSBooleanValue(DEFAULT_VALUE, false));
      }

   }

   public List getOrderedChildren() {
      return null;
   }

   static {
      DEFAULT_VALUE = Boolean.TRUE;
   }
}
