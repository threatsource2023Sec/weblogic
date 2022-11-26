package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.ExpressionType;

public class ConditionTypeImpl extends AbstractXACMLObject implements ConditionType {
   private ExpressionType expression;

   protected ConditionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public ExpressionType getExpression() {
      return this.expression;
   }

   public void setExpression(ExpressionType exp) {
      this.expression = (ExpressionType)this.prepareForAssignment(this.expression, exp);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.expression != null) {
         children.add(this.expression);
      }

      return Collections.unmodifiableList(children);
   }
}
