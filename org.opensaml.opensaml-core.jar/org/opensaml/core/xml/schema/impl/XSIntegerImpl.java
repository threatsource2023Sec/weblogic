package org.opensaml.core.xml.schema.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSInteger;

public class XSIntegerImpl extends AbstractXMLObject implements XSInteger {
   private Integer value;

   protected XSIntegerImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Integer getValue() {
      return this.value;
   }

   public void setValue(Integer newValue) {
      this.value = (Integer)this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return null;
   }
}
