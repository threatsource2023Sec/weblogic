package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface Response extends SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "Response", "ecp");
   String TYPE_LOCAL_NAME = "ResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "ResponseType", "ecp");
   String ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";

   String getAssertionConsumerServiceURL();

   void setAssertionConsumerServiceURL(String var1);
}
