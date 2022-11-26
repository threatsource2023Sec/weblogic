package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import org.w3c.dom.Node;

public class ExpressionHandler {
   private ExpressionHandler() {
   }

   public static Expression parseExpression(AttributeRegistry registry, Node node) throws URISyntaxException, DocumentParseException {
      if (node.getNodeName().equals("Apply")) {
         return new Apply(registry, node);
      } else if (node.getNodeName().equals("Condition")) {
         return new Condition(registry, node);
      } else if (node.getNodeName().equals("Function")) {
         return new Function(node);
      } else if (node.getNodeName().equals("ResourceAttributeDesignator")) {
         return new ResourceAttributeDesignator(node);
      } else if (node.getNodeName().equals("ActionAttributeDesignator")) {
         return new ActionAttributeDesignator(node);
      } else if (node.getNodeName().equals("SubjectAttributeDesignator")) {
         return new SubjectAttributeDesignator(node);
      } else if (node.getNodeName().equals("EnvironmentAttributeDesignator")) {
         return new EnvironmentAttributeDesignator(node);
      } else if (node.getNodeName().equals("AttributeSelector")) {
         return new AttributeSelector(node);
      } else {
         if (node.getNodeName().equals("AttributeValue")) {
            com.bea.common.security.xacml.attr.AttributeValue av = registry.getAttribute(node);
            if (av != null) {
               return new AttributeValue(av);
            }
         } else if (node.getNodeName().equals("VariableReference")) {
            return new VariableReference(node);
         }

         return null;
      }
   }
}
