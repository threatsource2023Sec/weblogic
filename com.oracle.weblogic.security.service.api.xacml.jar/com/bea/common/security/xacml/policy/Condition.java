package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.SchemaObject;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Condition extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = -7226434320286848507L;
   private Expression expression;

   public Condition(Expression expression) {
      this.expression = expression;
   }

   public Condition(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         this.expression = ExpressionHandler.parseExpression(registry, node);
         if (this.expression != null) {
            break;
         }
      }

   }

   public String getElementName() {
      return "Condition";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      ((SchemaObject)this.expression).encode(nsMap, ps);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Condition)) {
         return false;
      } else {
         Condition o = (Condition)other;
         return this.expression == o.expression || this.expression != null && this.expression.equals(o.expression);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.expression);
      return result;
   }

   public Expression getExpression() {
      return this.expression;
   }
}
