package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Status extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Status";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Status", "saml2p");
   String TYPE_LOCAL_NAME = "StatusType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusType", "saml2p");

   StatusCode getStatusCode();

   void setStatusCode(StatusCode var1);

   StatusMessage getStatusMessage();

   void setStatusMessage(StatusMessage var1);

   StatusDetail getStatusDetail();

   void setStatusDetail(StatusDetail var1);
}
