package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeSelector extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = -4710593618335266328L;
   private String requestContextPath;
   private URI dataType;
   private boolean mustBePresent;

   public AttributeSelector(URI dataType, String requestContextPath, boolean mustBePresent) {
      this.dataType = dataType;
      this.requestContextPath = requestContextPath;
      this.mustBePresent = mustBePresent;
   }

   public AttributeSelector(Node root) throws URISyntaxException {
      NamedNodeMap attrs = root.getAttributes();
      this.requestContextPath = attrs.getNamedItem("RequestContextPath").getNodeValue();

      try {
         this.dataType = new URI(attrs.getNamedItem("DateType").getNodeValue());
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }

      Node mbpNode = attrs.getNamedItem("MustBePresent");
      this.mustBePresent = mbpNode != null ? "true".equals(mbpNode.getNodeValue()) : false;
   }

   public String getElementName() {
      return "AttributeSelector";
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.requestContextPath != null) {
         ps.print(" RequestContextPath=\"");
         ps.print(this.escapeXML(this.requestContextPath));
         ps.print("\"");
      }

      if (this.dataType != null) {
         ps.print(" DataType=\"");
         ps.print(this.dataType);
         ps.print("\"");
      }

      if (this.mustBePresent) {
         ps.print(" MustBePresent=\"true\"");
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AttributeSelector)) {
         return false;
      } else {
         AttributeSelector o = (AttributeSelector)other;
         return (this.requestContextPath == o.requestContextPath || this.requestContextPath != null && this.requestContextPath.equals(o.requestContextPath)) && (this.dataType == o.dataType || this.dataType != null && this.dataType.equals(o.dataType)) && this.mustBePresent == o.mustBePresent;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.requestContextPath);
      result = HashCodeUtil.hash(result, this.dataType);
      result = HashCodeUtil.hash(result, this.mustBePresent);
      return result;
   }

   public String getRequestContextPath() {
      return this.requestContextPath;
   }

   public URI getDataType() {
      return this.dataType;
   }

   public boolean isMustBePresent() {
      return this.mustBePresent;
   }
}
