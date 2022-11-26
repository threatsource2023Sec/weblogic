package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResourceContent extends ContextSchemaObject {
   private static final long serialVersionUID = -3288143616817843257L;
   private NamedNodeMap attributes;
   private NodeList children;

   public ResourceContent(NamedNodeMap attributes, NodeList children) {
      this.attributes = attributes;
      this.children = children;
   }

   public ResourceContent(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      this.attributes = root.getAttributes();
      this.children = root.getChildNodes();
   }

   public String getElementName() {
      return "ResourceContent";
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.attributes != null) {
         ps.print(this.attributes.toString());
      }

   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.children != null) {
         ps.print(this.children.toString());
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ResourceContent)) {
         return false;
      } else {
         ResourceContent o = (ResourceContent)other;
         return (this.attributes == o.attributes || this.attributes != null && this.attributes.equals(o.attributes)) && (this.children == o.children || this.children != null && this.children.equals(o.children));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributes);
      result = HashCodeUtil.hash(result, this.children);
      return result;
   }

   public NamedNodeMap getAttributes() {
      return this.attributes;
   }

   public NodeList getChildren() {
      return this.children;
   }
}
