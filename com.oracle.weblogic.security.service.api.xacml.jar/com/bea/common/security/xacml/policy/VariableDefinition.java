package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.SchemaObject;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VariableDefinition extends PolicySchemaObject {
   private static final long serialVersionUID = -2231899539715852391L;
   private String variableId;
   private Expression expression;

   public VariableDefinition(String variableId, Expression expression) {
      this.variableId = variableId;
      this.expression = expression;
   }

   public VariableDefinition(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();
      this.variableId = attrs.getNamedItem("VariableId").getNodeValue();
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
      return "VariableDefinition";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" VariableId=\"");
      ps.print(this.escapeXML(this.variableId));
      ps.print('"');
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
      } else if (!(other instanceof VariableDefinition)) {
         return false;
      } else {
         VariableDefinition o = (VariableDefinition)other;
         return (this.variableId == o.variableId || this.variableId != null && this.variableId.equals(o.variableId)) && (this.expression == o.expression || this.expression != null && this.expression.equals(o.expression));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.variableId);
      result = HashCodeUtil.hash(result, this.expression);
      return result;
   }

   public String getId() {
      return this.variableId;
   }

   public Expression getExpression() {
      return this.expression;
   }
}
