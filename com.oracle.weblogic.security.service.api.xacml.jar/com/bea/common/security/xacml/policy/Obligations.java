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

public class Obligations extends PolicySchemaObject {
   private static final long serialVersionUID = -4937409216784603791L;
   private List obligations;

   public Obligations(List obligations) {
      this.obligations = obligations != null ? Collections.unmodifiableList(obligations) : null;
   }

   public Obligations(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List obligations = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Obligation")) {
            obligations.add(new Obligation(registry, node));
         }
      }

      this.obligations = Collections.unmodifiableList(obligations);
   }

   public String getElementName() {
      return "Obligations";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.obligations != null) {
         Iterator it = this.obligations.iterator();

         while(it.hasNext()) {
            ((Obligation)it.next()).encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Obligations)) {
         return false;
      } else {
         Obligations o = (Obligations)other;
         return CollectionUtil.equals(this.obligations, o.obligations);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.obligations);
      return result;
   }

   public List getObligations() {
      return this.obligations;
   }
}
