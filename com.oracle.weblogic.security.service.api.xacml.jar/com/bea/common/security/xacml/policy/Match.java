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

public abstract class Match extends PolicySchemaObject {
   private AttributeValue attributeValue;
   private URI matchId;
   private AttributeSelector selector;

   protected Match(URI matchId, AttributeValue attributeValue) {
      this(matchId, (AttributeValue)attributeValue, (AttributeSelector)null);
   }

   protected Match(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue) {
      this(matchId, (com.bea.common.security.xacml.attr.AttributeValue)attributeValue, (AttributeSelector)null);
   }

   public Match(URI matchId, com.bea.common.security.xacml.attr.AttributeValue attributeValue, AttributeSelector selector) {
      this(matchId, attributeValue != null ? new AttributeValue(attributeValue) : null, selector);
   }

   public Match(URI matchId, AttributeValue attributeValue, AttributeSelector selector) {
      this.matchId = matchId;
      this.attributeValue = attributeValue;
      this.selector = selector;
   }

   protected Match(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      try {
         this.matchId = new URI(root.getAttributes().getNamedItem("MatchId").getNodeValue());
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         String cname = this.getLocalName(node);
         if (cname.equals("AttributeValue")) {
            com.bea.common.security.xacml.attr.AttributeValue av = registry.getAttribute(node);
            if (av != null) {
               this.attributeValue = new AttributeValue(av);
            }
         } else if (cname.equals("AttributeSelector")) {
            this.selector = new AttributeSelector(node);
         }
      }

   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" MatchId=\"");
      ps.print(this.matchId);
      ps.print("\"");
   }

   protected void encodeLeadElements(Map nsMap, PrintStream ps) {
      this.attributeValue.encode(nsMap, ps);
   }

   protected void encodeTrailingElements(Map nsMap, PrintStream ps) {
      if (this.selector != null) {
         this.selector.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Match)) {
         return false;
      } else {
         Match o = (Match)other;
         return (this.attributeValue == o.attributeValue || this.attributeValue != null && this.attributeValue.equals(o.attributeValue)) && (this.matchId == o.matchId || this.matchId != null && this.matchId.equals(o.matchId)) && (this.selector == o.selector || this.selector != null && this.selector.equals(o.selector));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributeValue);
      result = HashCodeUtil.hash(result, this.matchId);
      result = HashCodeUtil.hash(result, this.selector);
      return result;
   }

   public AttributeValue getAttributeValue() {
      return this.attributeValue;
   }

   public URI getMatchId() {
      return this.matchId;
   }

   public AttributeSelector getAttributeSelector() {
      return this.selector;
   }
}
