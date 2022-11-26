package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ExpressionType;
import org.opensaml.xacml.policy.VariableDefinitionType;

public class VariableDefinitionTypeImpl extends AbstractXACMLObject implements VariableDefinitionType {
   private ExpressionType expression;
   private String variableId;

   protected VariableDefinitionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public ExpressionType getExpression() {
      return this.expression;
   }

   public void setExpression(ExpressionType newExpression) {
      this.expression = (ExpressionType)this.prepareForAssignment(this.expression, newExpression);
   }

   public String getVariableId() {
      return this.variableId;
   }

   public void setVariableId(String id) {
      this.variableId = this.prepareForAssignment(this.variableId, id);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.expression != null) {
         children.add(this.expression);
      }

      return Collections.unmodifiableList(children);
   }
}
