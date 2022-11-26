package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface StatusCodeType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusCode", "xacml-context");
   String TYPE_LOCAL_NAME = "StatusCodeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusCodeType", "xacml-context");
   String VALUE_ATTTRIB_NAME = "Value";
   String SC_MISSING_ATTRIBUTE = "urn:oasis:names:tc:xacml:1.0:status:missing-attribute";
   String SC_OK = "urn:oasis:names:tc:xacml:1.0:status:ok";
   String SC_PROCESSING_ERROR = "urn:oasis:names:tc:xacml:1.0:status:processing-error";
   String SC_SYNTAX_ERROR = "urn:oasis:names:tc:xacml:1.0:status:syntax-error";

   StatusCodeType getStatusCode();

   void setStatusCode(StatusCodeType var1);

   String getValue();

   void setValue(String var1);
}
