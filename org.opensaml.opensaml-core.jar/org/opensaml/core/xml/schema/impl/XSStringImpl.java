package org.opensaml.core.xml.schema.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSString;

public class XSStringImpl extends AbstractXMLObject implements XSString {
   private String value;

   protected XSStringImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String newValue) {
      this.value = this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return null;
   }
}
