package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import org.w3c.dom.Node;

public class ActionAttributeDesignator extends AttributeDesignator {
   private static final long serialVersionUID = -7170975793841703942L;

   public ActionAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent) {
      super(attributeId, dataType, mustBePresent);
   }

   public ActionAttributeDesignator(URI attributeId, URI dataType, boolean mustBePresent, String issuer) {
      super(attributeId, dataType, mustBePresent, issuer);
   }

   public ActionAttributeDesignator(Node root) throws URISyntaxException {
      super(root);
   }

   protected String getDesignatorType() {
      return "Action";
   }
}
