package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.Node;

public class PolicyIdReference extends IdReference {
   private static final long serialVersionUID = 3876771834791739200L;

   public PolicyIdReference(URI reference) {
      super(reference);
   }

   public PolicyIdReference(URI reference, String version) {
      super(reference, version);
   }

   public PolicyIdReference(URI reference, String version, String earliestVersion) {
      super(reference, version, earliestVersion);
   }

   public PolicyIdReference(URI reference, String version, String earliestVersion, String latestVersion) {
      super(reference, version, earliestVersion, latestVersion);
   }

   public PolicyIdReference(Node root) throws URISyntaxException {
      super(root);
   }

   public String getElementName() {
      return "PolicyIdReference";
   }

   public boolean hasBody() {
      return true;
   }

   public void encodeBody(PrintStream ps) {
      this.encodeValue(ps);
   }
}
