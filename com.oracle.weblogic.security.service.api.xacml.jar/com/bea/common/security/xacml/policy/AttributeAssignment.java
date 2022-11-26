package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import org.w3c.dom.Node;

public class AttributeAssignment extends PolicySchemaObject {
   private static final long serialVersionUID = -4417819160664904295L;
   private URI attributeId;
   private AttributeValue av;

   public AttributeAssignment(URI attributeId, com.bea.common.security.xacml.attr.AttributeValue av) {
      this(attributeId, av != null ? new AttributeValue(av) : null);
   }

   public AttributeAssignment(URI attributeId, AttributeValue av) {
      this.av = av;
      this.attributeId = attributeId;
   }

   public AttributeAssignment(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      com.bea.common.security.xacml.attr.AttributeValue av = registry.getAttribute(root);
      if (av != null) {
         this.av = new AttributeValue(av);
      }

      try {
         this.attributeId = new URI(root.getAttributes().getNamedItem("AttributeId").getNodeValue());
      } catch (java.net.URISyntaxException var5) {
         throw new URISyntaxException(var5);
      }
   }

   public String getElementName() {
      return "AttributeAssignment";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" AttributeId=\"");
      ps.print(this.attributeId);
      ps.print("\"");
      this.av.encodeDataType(ps);
   }

   public boolean hasBody() {
      return true;
   }

   public void encodeBody(PrintStream ps) {
      this.av.encodeValue(ps);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AttributeAssignment)) {
         return false;
      } else {
         AttributeAssignment o = (AttributeAssignment)other;
         return (this.attributeId == o.attributeId || this.attributeId != null && this.attributeId.equals(o.attributeId)) && (this.av == o.av || this.av != null && this.av.equals(o.av));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributeId);
      result = HashCodeUtil.hash(result, this.av);
      return result;
   }

   public URI getAttributeId() {
      return this.attributeId;
   }

   public AttributeValue getAttributeValue() {
      return this.av;
   }
}
