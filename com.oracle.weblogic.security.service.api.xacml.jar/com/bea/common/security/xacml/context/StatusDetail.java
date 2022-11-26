package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.SchemaObject;
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

public class StatusDetail extends ContextSchemaObject {
   private static final long serialVersionUID = -460015619953319698L;
   private List details;

   public StatusDetail(List details) {
      this.details = details;
   }

   public StatusDetail(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      List details = new ArrayList();
      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("MissingAttributeDetail")) {
            details.add(new MissingAttributeDetail(registry, child));
         }
      }

      this.details = details.isEmpty() ? null : Collections.unmodifiableList(details);
   }

   public String getElementName() {
      return "StatusDetail";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.details != null) {
         Iterator var3 = this.details.iterator();

         while(var3.hasNext()) {
            SchemaObject so = (SchemaObject)var3.next();
            so.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof StatusDetail)) {
         return false;
      } else {
         StatusDetail o = (StatusDetail)other;
         return CollectionUtil.equals(this.details, o.details);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.details);
      return result;
   }

   public List getStatusDetails() {
      return this.details;
   }
}
