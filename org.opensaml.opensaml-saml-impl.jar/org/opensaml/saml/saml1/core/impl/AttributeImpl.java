package org.opensaml.saml.saml1.core.impl;

import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Attribute;

public class AttributeImpl extends AttributeDesignatorImpl implements Attribute {
   private final XMLObjectChildrenList attributeValues = new XMLObjectChildrenList(this);

   protected AttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributeValues() {
      return this.attributeValues;
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.attributeValues);
   }
}
