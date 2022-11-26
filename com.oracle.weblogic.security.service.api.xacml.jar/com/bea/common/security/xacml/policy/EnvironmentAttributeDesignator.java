package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import org.w3c.dom.Node;

public class EnvironmentAttributeDesignator extends AttributeDesignator {
   private static final long serialVersionUID = -1807755039937768082L;

   public EnvironmentAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent) {
      super(attributeId, dataType, mustBePresent);
   }

   public EnvironmentAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer) {
      super(attributeId, dataType, mustBePresent, issuer);
   }

   public EnvironmentAttributeDesignator(Node root) throws URISyntaxException {
      super(root);
   }

   protected String getDesignatorType() {
      return "Environment";
   }
}
