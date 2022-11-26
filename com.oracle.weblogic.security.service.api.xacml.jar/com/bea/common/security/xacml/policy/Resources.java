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

public class Resources extends PolicySchemaObject {
   private static final long serialVersionUID = -2834648146634007658L;
   private List contents;

   public Resources(List resources) {
      this.contents = resources != null ? Collections.unmodifiableList(resources) : null;
   }

   public Resources(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List contents = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Resource")) {
            contents.add(new Resource(registry, node));
         }
      }

      this.contents = Collections.unmodifiableList(contents);
   }

   public String getElementName() {
      return "Resources";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.contents != null) {
         Iterator it = this.contents.iterator();

         while(it.hasNext()) {
            Resource m = (Resource)it.next();
            m.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Resources)) {
         return false;
      } else {
         Resources o = (Resources)other;
         return CollectionUtil.equals(this.contents, o.contents);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.contents);
      return result;
   }

   public List getResources() {
      return this.contents;
   }
}
