package org.opensaml.xacml.ctx;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface StatusType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Status";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "Status", "xacml-context");
   String TYPE_LOCAL_NAME = "StatusType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusType", "xacml-context");

   StatusCodeType getStatusCode();

   StatusDetailType getStatusDetail();

   StatusMessageType getStatusMessage();

   void setStatusCode(StatusCodeType var1);

   void setStatusDetail(StatusDetailType var1);

   void setStatusMessage(StatusMessageType var1);
}
