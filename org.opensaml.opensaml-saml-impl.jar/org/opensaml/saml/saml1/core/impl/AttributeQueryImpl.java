package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AttributeQuery;

public class AttributeQueryImpl extends SubjectQueryImpl implements AttributeQuery {
   private String resource;
   private final XMLObjectChildrenList attributeDesignators = new XMLObjectChildrenList(this);

   protected AttributeQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String res) {
      this.resource = this.prepareForAssignment(this.resource, res);
   }

   public List getAttributeDesignators() {
      return this.attributeDesignators;
   }

   public List getOrderedChildren() {
      List list = new ArrayList(this.attributeDesignators.size() + 1);
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      list.addAll(this.attributeDesignators);
      return Collections.unmodifiableList(list);
   }
}
