package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ResourceMatch extends Match {
   private static final long serialVersionUID = -4823199047093179191L;
   private ResourceAttributeDesignator designator;

   public ResourceMatch(URI matchId, AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public ResourceMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public ResourceMatch(URI matchId, AttributeValue attributeValue, ResourceAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public ResourceMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, ResourceAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public ResourceMatch(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      super(registry, root);
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("ResourceAttributeDesignator")) {
            this.designator = new ResourceAttributeDesignator(node);
         }
      }

   }

   public String getElementName() {
      return "ResourceMatch";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      this.encodeLeadElements(nsMap, ps);
      if (this.designator != null) {
         this.designator.encode(nsMap, ps);
      }

      this.encodeTrailingElements(nsMap, ps);
   }

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof ResourceMatch) {
         ResourceMatch o = (ResourceMatch)other;
         return this.designator == o.designator || this.designator != null && this.designator.equals(o.designator);
      } else {
         return false;
      }
   }

   public int internalHashCode() {
      int result = super.internalHashCode();
      result = HashCodeUtil.hash(result, this.designator);
      return result;
   }

   public ResourceAttributeDesignator getDesignator() {
      return this.designator;
   }
}
