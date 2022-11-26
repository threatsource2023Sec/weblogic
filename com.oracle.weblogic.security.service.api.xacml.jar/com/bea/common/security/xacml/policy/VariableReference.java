package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class VariableReference extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = -2031279526410195925L;
   private String variableId;

   public VariableReference(String variableId) {
      this.variableId = variableId;
   }

   public VariableReference(Node root) {
      NamedNodeMap attrs = root.getAttributes();
      this.variableId = attrs.getNamedItem("VariableId").getNodeValue();
   }

   public String getElementName() {
      return "VariableReference";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" VariableId=\"");
      ps.print(this.escapeXML(this.variableId));
      ps.print('"');
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof VariableReference)) {
         return false;
      } else {
         VariableReference o = (VariableReference)other;
         return this.variableId == o.variableId || this.variableId != null && this.variableId.equals(o.variableId);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.variableId);
      return result;
   }

   public String getId() {
      return this.variableId;
   }
}
