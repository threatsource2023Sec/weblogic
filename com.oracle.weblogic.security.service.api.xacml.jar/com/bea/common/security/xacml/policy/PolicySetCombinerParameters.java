package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.util.List;
import org.w3c.dom.Node;

public class PolicySetCombinerParameters extends AbstractPolicyCombinerParameters {
   private static final long serialVersionUID = 190355176946809099L;

   public PolicySetCombinerParameters(List combinerParameters, URI idRef) {
      super(combinerParameters, idRef);
   }

   public PolicySetCombinerParameters(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      super(registry, root, "PolicySet");
   }
}
