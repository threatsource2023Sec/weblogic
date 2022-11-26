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

public class Response extends ContextSchemaObject {
   private static final long serialVersionUID = 7313125996953919175L;
   private List results;

   public Response(List results) {
      this.results = results != null ? Collections.unmodifiableList(results) : null;
   }

   public Response(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      this.results = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("Result")) {
            this.results.add(new Result(registry, node));
         }
      }

      this.results = this.results.isEmpty() ? null : Collections.unmodifiableList(this.results);
   }

   public String getElementName() {
      return "Response";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.results != null) {
         Iterator it = this.results.iterator();

         while(it.hasNext()) {
            Result r = (Result)it.next();
            r.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Response)) {
         return false;
      } else {
         Response o = (Response)other;
         return CollectionUtil.equals(this.results, o.results);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.results);
      return result;
   }

   public List getResults() {
      return this.results;
   }
}
