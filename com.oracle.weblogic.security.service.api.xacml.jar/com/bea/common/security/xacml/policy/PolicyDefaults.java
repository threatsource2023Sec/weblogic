package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.DocumentParseException;
import org.w3c.dom.Node;

public class PolicyDefaults extends Defaults {
   private static final long serialVersionUID = 2128285701133725161L;

   public PolicyDefaults(String xPathVersion) {
      super(xPathVersion);
   }

   public PolicyDefaults(Node root) throws DocumentParseException {
      super(root);
   }

   protected String getPolicyPrefix() {
      return "Policy";
   }
}
