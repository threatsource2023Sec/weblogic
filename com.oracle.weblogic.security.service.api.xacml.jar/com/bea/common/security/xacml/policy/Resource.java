package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Resource extends PolicySchemaObject {
   private static final long serialVersionUID = 9115133257858295123L;
   private List matches;

   public Resource(List matches) {
      this.matches = matches != null ? Collections.unmodifiableList(matches) : null;
   }

   public Resource(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List matches = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("ResourceMatch")) {
            matches.add(new ResourceMatch(registry, node));
         }
      }

      this.matches = Collections.unmodifiableList(matches);
   }

   public String getElementName() {
      return "Resource";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.matches != null) {
         Iterator it = this.matches.iterator();

         while(it.hasNext()) {
            ResourceMatch m = (ResourceMatch)it.next();
            m.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Resource)) {
         return false;
      } else {
         Resource o = (Resource)other;
         return CollectionUtil.equals(this.matches, o.matches);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.matches);
      return result;
   }

   public List getMatches() {
      return this.matches;
   }
}
