package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Function extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = -5677494545236184388L;
   private URI functionId;

   public Function(URI functionId) {
      this.functionId = functionId;
   }

   public Function(Node root) throws URISyntaxException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.functionId = new URI(attrs.getNamedItem("FunctionId").getNodeValue());
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }
   }

   public String getElementName() {
      return "Function";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" FunctionId=\"");
      ps.print(this.functionId);
      ps.print("\"");
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Function)) {
         return false;
      } else {
         Function o = (Function)other;
         return this.functionId == o.functionId || this.functionId != null && this.functionId.equals(o.functionId);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.functionId);
      return result;
   }

   public URI getFunctionId() {
      return this.functionId;
   }
}
