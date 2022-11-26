package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import org.w3c.dom.Node;

public class ResourceAttributeDesignator extends AttributeDesignator {
   private static final long serialVersionUID = -8060346002381079861L;

   public ResourceAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent) {
      super(attributeId, dataType, mustBePresent);
   }

   public ResourceAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer) {
      super(attributeId, dataType, mustBePresent, issuer);
   }

   public ResourceAttributeDesignator(Node root) throws URISyntaxException {
      super(root);
   }

   protected String getDesignatorType() {
      return "Resource";
   }
}
