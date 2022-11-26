package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.ObligationsType;

public interface ResultType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Result";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Result", "xacml-context");
   String TYPE_LOCAL_NAME = "ResultType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "ResultType", "xacml-context");
   String RESOURCE_ID_ATTTRIB_NAME = "ResourceId";

   DecisionType getDecision();

   ObligationsType getObligations();

   void setObligations(ObligationsType var1);

   String getResourceId();

   StatusType getStatus();

   void setStatus(StatusType var1);

   void setDecision(DecisionType var1);

   void setResourceId(String var1);
}
