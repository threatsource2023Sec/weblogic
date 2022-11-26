package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ApplyType;

public class ApplyTypeImpl extends AbstractXACMLObject implements ApplyType {
   private XMLObjectChildrenList expressions = new XMLObjectChildrenList(this);
   private String functionId;

   protected ApplyTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getExpressions() {
      return this.expressions;
   }

   public String getFunctionId() {
      return this.functionId;
   }

   public void setFunctionId(String id) {
      this.functionId = this.prepareForAssignment(this.functionId, id);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (!this.expressions.isEmpty()) {
         children.addAll(this.expressions);
      }

      return Collections.unmodifiableList(children);
   }
}
