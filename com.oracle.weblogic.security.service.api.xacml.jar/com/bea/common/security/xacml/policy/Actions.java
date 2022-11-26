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

public class Actions extends PolicySchemaObject {
   private static final long serialVersionUID = 7219203161667986244L;
   private List contents;

   public Actions(List actions) {
      this.contents = actions != null ? Collections.unmodifiableList(actions) : null;
   }

   public Actions(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List contents = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Action")) {
            contents.add(new Action(registry, node));
         }
      }

      this.contents = Collections.unmodifiableList(contents);
   }

   public String getElementName() {
      return "Actions";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.contents != null) {
         Iterator it = this.contents.iterator();

         while(it.hasNext()) {
            Action m = (Action)it.next();
            m.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Actions)) {
         return false;
      } else {
         Actions o = (Actions)other;
         return CollectionUtil.equals(this.contents, o.contents);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.contents);
      return result;
   }

   public List getActions() {
      return this.contents;
   }
}
