package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Status extends ContextSchemaObject {
   private static final long serialVersionUID = 5529201125198511360L;
   private static final String OK_CODE = "urn:oasis:names:tc:xacml:1.0:status:ok";
   private StatusCode code;
   private String message;
   private StatusDetail detail;

   public Status(StatusCode code) {
      this(code, (String)null, (StatusDetail)null);
   }

   public Status(StatusCode code, String message) {
      this(code, message, (StatusDetail)null);
   }

   public Status(StatusCode code, StatusDetail detail) {
      this(code, (String)null, detail);
   }

   public Status(StatusCode code, String message, StatusDetail detail) {
      this.code = code;
      this.message = message;
      this.detail = detail;
   }

   public Status(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("StatusMessage")) {
            this.message = child.getFirstChild().getNodeValue();
         } else if (cname.equals("StatusCode")) {
            this.code = new StatusCode(registry, child);
         } else if (cname.equals("StatusDetail")) {
            this.detail = new StatusDetail(registry, child);
         }
      }

   }

   public String getElementName() {
      return "Status";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.code != null) {
         this.code.encode(nsMap, ps);
      }

      if (this.message != null) {
         ps.print("<StatusMessage>");
         ps.print(this.escapeXML(this.message));
         ps.print("</StatusMessage>");
      }

      if (this.detail != null) {
         this.detail.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Status)) {
         return false;
      } else {
         boolean var10000;
         label39: {
            Status o = (Status)other;
            if (this.code != o.code) {
               if (this.code != null) {
                  if (!this.code.equals(o.code)) {
                     break label39;
                  }
               } else if (!this.isOK(o)) {
                  break label39;
               }
            }

            if (this.detail == o.detail || this.detail != null && this.detail.equals(o.detail)) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public boolean isOK(Status o) {
      if (o != null && o.code != null) {
         URI v = o.code.getValue();
         return v == null || "urn:oasis:names:tc:xacml:1.0:status:ok".equals(v.toString());
      } else {
         return true;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.code);
      result = HashCodeUtil.hash(result, this.detail);
      return result;
   }

   public StatusCode getStatusCode() {
      return this.code;
   }

   public String getStatusMessage() {
      return this.message;
   }

   public StatusDetail getStatusDetail() {
      return this.detail;
   }
}
