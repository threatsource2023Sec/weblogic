package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.VariableReferenceType;

public class VariableReferenceTypeImpl extends AbstractXACMLObject implements VariableReferenceType {
   private XMLObjectChildrenList expressions = new XMLObjectChildrenList(this);
   private String valiableId;

   protected VariableReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getExpressions() {
      return this.expressions;
   }

   public String getVariableId() {
      return this.valiableId;
   }

   public void setVariableId(String id) {
      this.valiableId = this.prepareForAssignment(this.valiableId, id);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (!this.expressions.isEmpty()) {
         children.addAll(this.expressions);
      }

      return Collections.unmodifiableList(children);
   }
}
