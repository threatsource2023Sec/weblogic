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

public class Environment extends PolicySchemaObject {
   private static final long serialVersionUID = -5026629133898268565L;
   private List matches;

   public Environment(List matches) {
      this.matches = matches != null ? Collections.unmodifiableList(matches) : null;
   }

   public Environment(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List matches = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("EnvironmentMatch")) {
            matches.add(new EnvironmentMatch(registry, node));
         }
      }

      this.matches = Collections.unmodifiableList(matches);
   }

   public String getElementName() {
      return "Environment";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.matches != null) {
         Iterator it = this.matches.iterator();

         while(it.hasNext()) {
            EnvironmentMatch m = (EnvironmentMatch)it.next();
            m.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Environment)) {
         return false;
      } else {
         Environment o = (Environment)other;
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
