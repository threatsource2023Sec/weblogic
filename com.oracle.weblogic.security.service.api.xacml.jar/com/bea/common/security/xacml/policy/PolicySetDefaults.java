package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.DocumentParseException;
import org.w3c.dom.Node;

public class PolicySetDefaults extends Defaults {
   private static final long serialVersionUID = 7409737423006673421L;

   public PolicySetDefaults(String xPathVersion) {
      super(xPathVersion);
   }

   public PolicySetDefaults(Node root) throws DocumentParseException {
      super(root);
   }

   protected String getPolicyPrefix() {
      return "PolicySet";
   }
}
