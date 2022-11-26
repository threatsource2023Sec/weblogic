package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Obligation extends PolicySchemaObject {
   private static final long serialVersionUID = 1907803486459482543L;
   public static final String PERMIT = "Permit";
   public static final String DENY = "Deny";
   private URI obligationId;
   private boolean isFulfillOnPermit;
   private List attributeAssignments;

   public Obligation(URI obligationId, boolean isFulfillOnPermit) {
      this(obligationId, isFulfillOnPermit, (List)null);
   }

   public Obligation(URI obligationId, boolean isFulfillOnPermit, List attributeAssignments) {
      this.obligationId = obligationId;
      this.isFulfillOnPermit = isFulfillOnPermit;
      this.attributeAssignments = attributeAssignments != null ? Collections.unmodifiableList(attributeAssignments) : null;
   }

   public Obligation(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.obligationId = new URI(attrs.getNamedItem("ObligationId").getNodeValue());
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      this.isFulfillOnPermit = "Permit".equals(attrs.getNamedItem("FulfillOn").getNodeValue());
      List attributeAssignments = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("AttributeAssignment")) {
            attributeAssignments.add(new AttributeAssignment(registry, node));
         }
      }

      this.attributeAssignments = Collections.unmodifiableList(attributeAssignments);
   }

   public String getElementName() {
      return "Obligation";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" ObligationId=\"");
      ps.print(this.obligationId);
      ps.print("\" FulfillOn=\"");
      ps.print(this.isFulfillOnPermit ? "Permit" : "Deny");
      ps.print("\"");
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.attributeAssignments != null) {
         Iterator it = this.attributeAssignments.iterator();

         while(it.hasNext()) {
            ((AttributeAssignment)it.next()).encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Obligation)) {
         return false;
      } else {
         Obligation o = (Obligation)other;
         return (this.obligationId == o.obligationId || this.obligationId != null && this.obligationId.equals(o.obligationId)) && this.isFulfillOnPermit == o.isFulfillOnPermit && CollectionUtil.equals(this.attributeAssignments, o.attributeAssignments);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.obligationId);
      result = HashCodeUtil.hash(result, this.isFulfillOnPermit);
      result = HashCodeUtil.hash(result, this.attributeAssignments);
      return result;
   }

   public URI getId() {
      return this.obligationId;
   }

   public boolean isFulfillOnPermit() {
      return this.isFulfillOnPermit;
   }

   public List getAttributeAssignments() {
      return this.attributeAssignments;
   }
}
