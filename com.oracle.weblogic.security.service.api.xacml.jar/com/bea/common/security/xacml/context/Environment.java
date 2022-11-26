package com.bea.common.security.xacml.context;

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

public class Environment extends ContextSchemaObject {
   private static final long serialVersionUID = -7415442889724927836L;
   private List attributes;

   public Environment() {
      this((List)null);
   }

   public Environment(List attributes) {
      this.attributes = attributes != null ? Collections.unmodifiableList(attributes) : null;
   }

   public Environment(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      this.attributes = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Attribute")) {
            this.attributes.add(new Attribute(registry, node));
         }
      }

      this.attributes = this.attributes.isEmpty() ? null : Collections.unmodifiableList(this.attributes);
   }

   public String getElementName() {
      return "Environment";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.attributes != null) {
         Iterator it = this.attributes.iterator();

         while(it.hasNext()) {
            Attribute a = (Attribute)it.next();
            a.encode(nsMap, ps);
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
         return CollectionUtil.equals(this.attributes, o.attributes);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributes);
      return result;
   }

   public List getAttributes() {
      return this.attributes;
   }
}
