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

public class Resource extends ContextSchemaObject {
   private static final long serialVersionUID = -5570257964575800519L;
   private ResourceContent rc;
   private List attributes;

   public Resource() {
      this((ResourceContent)((ResourceContent)null), (List)null);
   }

   public Resource(ResourceContent rc) {
      this((ResourceContent)rc, (List)null);
   }

   public Resource(List attributes) {
      this((ResourceContent)null, (List)attributes);
   }

   public Resource(ResourceContent rc, List attributes) {
      this.rc = rc;
      this.attributes = attributes != null ? Collections.unmodifiableList(attributes) : null;
   }

   public Resource(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      this.attributes = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Attribute")) {
            this.attributes.add(new Attribute(registry, node));
         } else if (node.getNodeName().equals("ResourceContent")) {
            this.rc = new ResourceContent(registry, node);
         }
      }

      this.attributes = this.attributes.isEmpty() ? null : Collections.unmodifiableList(this.attributes);
   }

   public String getElementName() {
      return "Resource";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.rc != null) {
         this.rc.encode(nsMap, ps);
      }

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
      } else if (!(other instanceof Resource)) {
         return false;
      } else {
         Resource o = (Resource)other;
         return (this.rc == o.rc || this.rc != null && this.rc.equals(o.rc)) && CollectionUtil.equals(this.attributes, o.attributes);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.rc);
      result = HashCodeUtil.hash(result, this.attributes);
      return result;
   }

   public ResourceContent getResourceContent() {
      return this.rc;
   }

   public List getAttributes() {
      return this.attributes;
   }
}
