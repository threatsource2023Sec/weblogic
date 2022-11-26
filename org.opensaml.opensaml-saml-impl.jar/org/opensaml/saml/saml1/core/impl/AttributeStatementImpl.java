package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AttributeStatement;

public class AttributeStatementImpl extends SubjectStatementImpl implements AttributeStatement {
   private final XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected AttributeStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getOrderedChildren() {
      List list = new ArrayList(this.attributes.size() + 1);
      if (super.getOrderedChildren() != null) {
         list.addAll(super.getOrderedChildren());
      }

      list.addAll(this.attributes);
      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
