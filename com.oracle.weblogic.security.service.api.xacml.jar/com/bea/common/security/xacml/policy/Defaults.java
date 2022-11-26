package com.bea.common.security.xacml.policy;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Defaults extends PolicySchemaObject {
   public static final String XPATH_1_0_VERSION = "http://www.w3.org/TR/1999/Rec-xpath-19991116";
   private String xPathVersion;

   public Defaults(String xPathVersion) {
      this.xPathVersion = xPathVersion;
   }

   public Defaults(Node root) throws DocumentParseException {
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("XPathVersion")) {
            this.xPathVersion = node.getFirstChild().getNodeValue();
            if (!this.xPathVersion.equals("http://www.w3.org/TR/1999/Rec-xpath-19991116")) {
               throw new DocumentParseException(ApiLogger.getIncorrectXPathVersion());
            }
         }
      }

   }

   protected abstract String getPolicyPrefix();

   public String getElementName() {
      return this.getPolicyPrefix() + "Defaults";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.xPathVersion != null) {
         ps.print("<XPathVersion>");
         ps.print(this.escapeXML(this.xPathVersion));
         ps.print("</XPathVersion>");
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Defaults)) {
         return false;
      } else {
         Defaults o = (Defaults)other;
         return this.xPathVersion == o.xPathVersion || this.xPathVersion != null && this.xPathVersion.equals(o.xPathVersion);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.xPathVersion);
      return result;
   }

   public String getXPathVersion() {
      return this.xPathVersion;
   }
}
