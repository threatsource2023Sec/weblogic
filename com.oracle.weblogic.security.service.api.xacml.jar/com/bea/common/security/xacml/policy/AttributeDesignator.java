package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AttributeDesignator extends PolicySchemaObject implements Expression {
   private URI attributeId;
   private URI dataType;
   private String issuer;
   private boolean mustBePresent;

   public AttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent) {
      this(attributeId, dataType, mustBePresent, (String)null);
   }

   public AttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer) {
      this.attributeId = attributeId;
      this.dataType = dataType;
      this.mustBePresent = mustBePresent;
      this.issuer = issuer;
   }

   protected AttributeDesignator(Node root) throws URISyntaxException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.attributeId = new URI(attrs.getNamedItem("AttributeId").getNodeValue());
         this.dataType = new URI(attrs.getNamedItem("DataType").getNodeValue());
      } catch (java.net.URISyntaxException var5) {
         throw new URISyntaxException(var5);
      }

      Node issuerNode = attrs.getNamedItem("Issuer");
      if (issuerNode != null) {
         this.issuer = issuerNode.getNodeValue();
      }

      Node mbpNode = attrs.getNamedItem("MustBePresent");
      this.mustBePresent = mbpNode != null ? "true".equals(mbpNode.getNodeValue()) : false;
   }

   protected abstract String getDesignatorType();

   public String getElementName() {
      return this.getDesignatorType() + "AttributeDesignator";
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.attributeId != null) {
         ps.print(" AttributeId=\"");
         ps.print(this.attributeId);
         ps.print("\"");
      }

      if (this.dataType != null) {
         ps.print(" DataType=\"");
         ps.print(this.dataType);
         ps.print("\"");
      }

      if (this.issuer != null) {
         ps.print(" Issuer=\"");
         ps.print(this.escapeXML(this.issuer));
         ps.print("\"");
      }

      if (this.mustBePresent) {
         ps.print(" MustBePresent=\"true\"");
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AttributeDesignator)) {
         return false;
      } else {
         AttributeDesignator o = (AttributeDesignator)other;
         return (this.attributeId == o.attributeId || this.attributeId != null && this.attributeId.equals(o.attributeId)) && (this.dataType == o.dataType || this.dataType != null && this.dataType.equals(o.dataType)) && (this.issuer == o.issuer || this.issuer != null && this.issuer.equals(o.issuer)) && this.mustBePresent == o.mustBePresent;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributeId);
      result = HashCodeUtil.hash(result, this.dataType);
      result = HashCodeUtil.hash(result, this.issuer);
      result = HashCodeUtil.hash(result, this.mustBePresent);
      return result;
   }

   public URI getAttributeId() {
      return this.attributeId;
   }

   public URI getDataType() {
      return this.dataType;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public boolean isMustBePresent() {
      return this.mustBePresent;
   }
}
