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

public class CombinerParameters extends PolicySchemaObject {
   private static final long serialVersionUID = 1911437923550411399L;
   private List combinerParameters;

   public CombinerParameters(List combinerParameters) {
      this.combinerParameters = combinerParameters != null ? Collections.unmodifiableList(combinerParameters) : null;
   }

   public CombinerParameters(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List combinerParameters = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("CombinerParameter")) {
            combinerParameters.add(new CombinerParameter(registry, node));
         }
      }

      this.combinerParameters = Collections.unmodifiableList(combinerParameters);
   }

   public String getElementName() {
      return "CombinerParameters";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.combinerParameters != null) {
         Iterator it = this.combinerParameters.iterator();

         while(it.hasNext()) {
            ((CombinerParameter)it.next()).encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof CombinerParameters)) {
         return false;
      } else {
         CombinerParameters o = (CombinerParameters)other;
         return CollectionUtil.equals(this.combinerParameters, o.combinerParameters);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.combinerParameters);
      return result;
   }

   private List getCombinerParameters() {
      return this.combinerParameters;
   }
}
