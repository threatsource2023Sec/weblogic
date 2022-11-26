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

public class SubjectMatch extends Match {
   private static final long serialVersionUID = 2207001521170783568L;
   private SubjectAttributeDesignator designator;

   public SubjectMatch(URI matchId, AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public SubjectMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, AttributeSelector selector) {
      super(matchId, attributeValue, selector);
   }

   public SubjectMatch(URI matchId, AttributeValue attributeValue, SubjectAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public SubjectMatch(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, SubjectAttributeDesignator designator) {
      super(matchId, attributeValue);
      this.designator = designator;
   }

   public SubjectMatch(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      super(registry, root);
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("SubjectAttributeDesignator")) {
            this.designator = new SubjectAttributeDesignator(node);
         }
      }

   }

   public String getElementName() {
      return "SubjectMatch";
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
      if (super.equals(other) && other instanceof SubjectMatch) {
         SubjectMatch o = (SubjectMatch)other;
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

   public SubjectAttributeDesignator getDesignator() {
      return this.designator;
   }
}
