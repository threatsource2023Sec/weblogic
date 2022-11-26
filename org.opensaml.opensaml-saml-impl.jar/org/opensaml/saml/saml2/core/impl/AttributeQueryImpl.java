package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.AttributeQuery;

public class AttributeQueryImpl extends SubjectQueryImpl implements AttributeQuery {
   private final XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected AttributeQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      children.addAll(this.attributes);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
