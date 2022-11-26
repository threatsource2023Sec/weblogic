package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.Obligations;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Result extends ContextSchemaObject {
   private static final long serialVersionUID = 4515145048060511003L;
   public static final int PERMIT = 0;
   public static final int DENY = 1;
   public static final int INDETERMINATE = 2;
   public static final int NOT_APPLICABLE = 3;
   private static final String PERMIT_VALUE = "Permit";
   private static final String DENY_VALUE = "Deny";
   private static final String INDETERMINATE_VALUE = "Indeterminate";
   private static final String NOT_APPLICABLE_VALUE = "NotApplicable";
   private int decision;
   private Status status;
   private Obligations obligations;
   private String resourceId;

   public Result(int decision) {
      this(decision, (Status)null, (Obligations)null, (String)null);
   }

   public Result(int decision, Status status) {
      this(decision, status, (Obligations)null, (String)null);
   }

   public Result(int decision, Obligations obligations) {
      this(decision, (Status)null, obligations, (String)null);
   }

   public Result(int decision, String resourceId) {
      this(decision, (Status)null, (Obligations)null, resourceId);
   }

   public Result(int decision, Obligations obligations, String resourceId) {
      this(decision, (Status)null, obligations, resourceId);
   }

   public Result(int decision, Status status, String resourceId) {
      this(decision, status, (Obligations)null, resourceId);
   }

   public Result(int decision, Status status, Obligations obligations) {
      this(decision, status, obligations, (String)null);
   }

   public Result(int decision, Status status, Obligations obligations, String resourceId) {
      this.decision = decision;
      this.status = status;
      this.obligations = obligations;
      this.resourceId = resourceId;
   }

   public Result(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();
      Node node = attrs.getNamedItem("RuleId");
      if (node != null) {
         this.resourceId = node.getNodeValue();
      }

      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("Decision")) {
            String d = child.getFirstChild().getNodeValue();
            if ("Permit".equals(d)) {
               this.decision = 0;
            } else if ("Indeterminate".equals(d)) {
               this.decision = 2;
            } else if ("NotApplicable".equals(d)) {
               this.decision = 3;
            } else {
               this.decision = 1;
            }
         } else if (cname.equals("Obligations")) {
            this.obligations = new Obligations(registry, child);
         } else if (cname.equals("Status")) {
            this.status = new Status(registry, child);
         }
      }

   }

   public String getElementName() {
      return "Result";
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.resourceId != null) {
         ps.print(" ResourceId=\"");
         ps.print(this.escapeXML(this.resourceId));
         ps.print("\"");
      }

   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      ps.print("<Decision>");
      switch (this.decision) {
         case 0:
            ps.print("Permit");
            break;
         case 1:
            ps.print("Deny");
            break;
         case 2:
            ps.print("Indeterminate");
            break;
         case 3:
            ps.print("NotApplicable");
      }

      ps.print("</Decision>");
      if (this.status != null) {
         this.status.encode(nsMap, ps);
      }

      if (this.obligations != null) {
         this.obligations.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Result)) {
         return false;
      } else {
         Result o = (Result)other;
         return this.decision == o.decision && (this.status == o.status || this.status != null && this.status.equals(o.status)) && (this.obligations == o.obligations || this.obligations != null && this.obligations.equals(o.obligations)) && (this.resourceId == o.resourceId || this.resourceId != null && this.resourceId.equals(o.resourceId));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.decision);
      result = HashCodeUtil.hash(result, this.status);
      result = HashCodeUtil.hash(result, this.obligations);
      result = HashCodeUtil.hash(result, this.resourceId);
      return result;
   }

   public int getDecision() {
      return this.decision;
   }

   public Status getStatus() {
      return this.status;
   }

   public Obligations getObligations() {
      return this.obligations;
   }

   public String getResourceId() {
      return this.resourceId;
   }
}
