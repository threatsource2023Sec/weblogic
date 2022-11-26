package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.Node;

public class PolicySetIdReference extends IdReference {
   private static final long serialVersionUID = -6907895688117490691L;

   public PolicySetIdReference(URI reference) {
      super(reference);
   }

   public PolicySetIdReference(URI reference, String version) {
      super(reference, version);
   }

   public PolicySetIdReference(URI reference, String version, String earliestVersion) {
      super(reference, version, earliestVersion);
   }

   public PolicySetIdReference(URI reference, String version, String earliestVersion, String latestVersion) {
      super(reference, version, earliestVersion, latestVersion);
   }

   public PolicySetIdReference(Node root) throws URISyntaxException {
      super(root);
   }

   public String getElementName() {
      return "PolicySetIdReference";
   }

   public boolean hasBody() {
      return true;
   }

   public void encodeBody(PrintStream ps) {
      this.encodeValue(ps);
   }
}
