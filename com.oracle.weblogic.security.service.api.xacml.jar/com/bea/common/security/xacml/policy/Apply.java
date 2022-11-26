package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.SchemaObject;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Apply extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = -152464173632063058L;
   private URI functionId;
   private List expressions;

   public Apply(URI functionId, List expressions) {
      this.functionId = functionId;
      this.expressions = expressions != null ? Collections.unmodifiableList(expressions) : null;
   }

   public Apply(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.functionId = new URI(attrs.getNamedItem("FunctionId").getNodeValue());
      } catch (java.net.URISyntaxException var9) {
         throw new URISyntaxException(var9);
      }

      List expressions = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         Expression e = ExpressionHandler.parseExpression(registry, node);
         if (e != null) {
            expressions.add(e);
         }
      }

      this.expressions = Collections.unmodifiableList(expressions);
   }

   public String getElementName() {
      return "Apply";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" FunctionId=\"");
      ps.print(this.functionId);
      ps.print('"');
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.expressions != null) {
         Iterator it = this.expressions.iterator();

         while(it.hasNext()) {
            ((SchemaObject)it.next()).encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Apply)) {
         return false;
      } else {
         Apply o = (Apply)other;
         return (this.functionId == o.functionId || this.functionId != null && this.functionId.equals(o.functionId)) && CollectionUtil.equals(this.expressions, o.expressions);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.functionId);
      result = HashCodeUtil.hash(result, this.expressions);
      return result;
   }

   public URI getFunctionId() {
      return this.functionId;
   }

   public List getExpressions() {
      return this.expressions;
   }
}
