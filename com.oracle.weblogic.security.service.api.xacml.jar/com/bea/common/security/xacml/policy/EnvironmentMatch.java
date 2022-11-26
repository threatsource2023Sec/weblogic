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

public class EnvironmentMatch extends Match {
   private static final long serialVersionUID = 8261217908742811601L;
   private EnvironmentAttributeDesignator designator;

   public EnvironmentMatch(URI matchId, AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public EnvironmentMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public EnvironmentMatch(URI matchId, AttributeValue attributeValue, EnvironmentAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public EnvironmentMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, EnvironmentAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public EnvironmentMatch(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      super(registry, root);
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("EnvironmentAttributeDesignator")) {
            this.designator = new EnvironmentAttributeDesignator(node);
         }
      }

   }

   public String getElementName() {
      return "EnvironmentMatch";
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
      if (super.equals(other) && other instanceof EnvironmentMatch) {
         EnvironmentMatch o = (EnvironmentMatch)other;
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

   public EnvironmentAttributeDesignator getDesignator() {
      return this.designator;
   }
}
