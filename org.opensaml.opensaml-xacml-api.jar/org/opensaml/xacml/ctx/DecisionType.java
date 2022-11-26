package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface DecisionType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Decision";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Decision", "xacml-context");
   String TYPE_LOCAL_NAME = "DecisionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "DecisionType", "xacml-context");

   DECISION getDecision();

   void setDecision(DECISION var1);

   public static enum DECISION {
      Deny,
      Permit,
      Indeterminate,
      NotApplicable;
   }
}
