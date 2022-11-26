package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CombinerParameter extends PolicySchemaObject {
   private static final long serialVersionUID = -2434599351735811678L;
   private String parameterName;
   private AttributeValue attributeValue;

   public CombinerParameter(String parameterName, com.bea.common.security.xacml.attr.AttributeValue attributeValue) {
      this(parameterName, attributeValue != null ? new AttributeValue(attributeValue) : null);
   }

   public CombinerParameter(String parameterName, AttributeValue attributeValue) {
      this.parameterName = parameterName;
      this.attributeValue = attributeValue;
   }

   public CombinerParameter(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();
      this.parameterName = attrs.getNamedItem("ParameterName").getNodeValue();
      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("AttributeValue")) {
            com.bea.common.security.xacml.attr.AttributeValue av = registry.getAttribute(child);
            if (av != null) {
               this.attributeValue = new AttributeValue(av);
            }
         }
      }

   }

   public String getElementName() {
      return "CombinerParameter";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" ParameterName=\"");
      ps.print(this.escapeXML(this.parameterName));
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.attributeValue != null) {
         this.attributeValue.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof CombinerParameter)) {
         return false;
      } else {
         CombinerParameter o = (CombinerParameter)other;
         return (this.parameterName == o.parameterName || this.parameterName != null && this.parameterName.equals(o.parameterName)) && (this.attributeValue == o.attributeValue || this.attributeValue != null && this.attributeValue.equals(o.attributeValue));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.parameterName);
      result = HashCodeUtil.hash(result, this.attributeValue);
      return result;
   }

   public String getParameterName() {
      return this.parameterName;
   }

   public AttributeValue getAttributeValue() {
      return this.attributeValue;
   }
}
