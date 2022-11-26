package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StatusCode extends ContextSchemaObject {
   private static final long serialVersionUID = 4530626546880091118L;
   private static final String OK_CODE = "urn:oasis:names:tc:xacml:1.0:status:ok";
   private StatusCode code;
   private URI value;

   public StatusCode(URI value) {
      this((URI)value, (StatusCode)null);
   }

   public StatusCode(URI value, StatusCode code) {
      this.value = value;
      this.code = code;
   }

   public StatusCode(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.value = new URI(attrs.getNamedItem("Value").getNodeValue());
      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }

      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("StatusCode")) {
            this.code = new StatusCode(registry, child);
         }
      }

   }

   public String getElementName() {
      return "StatusCode";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" Value=\"");
      ps.print(this.value);
      ps.print("\"");
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.code != null) {
         this.code.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other == null && (this.value == null || "urn:oasis:names:tc:xacml:1.0:status:ok".equals(this.value.toString()))) {
         return true;
      } else if (!(other instanceof StatusCode)) {
         return false;
      } else {
         StatusCode o = (StatusCode)other;
         return (this.value == o.value || this.value != null && this.value.equals(o.value)) && (this.code == o.code || this.code != null && this.code.equals(o.code));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.value);
      result = HashCodeUtil.hash(result, this.code);
      return result;
   }

   public URI getValue() {
      return this.value;
   }

   public StatusCode getStatusCode() {
      return this.code;
   }
}
