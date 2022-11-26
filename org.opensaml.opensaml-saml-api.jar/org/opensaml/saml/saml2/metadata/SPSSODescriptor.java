package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface SPSSODescriptor extends SSODescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SPSSODescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptor", "md");
   String TYPE_LOCAL_NAME = "SPSSODescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptorType", "md");
   String AUTH_REQUESTS_SIGNED_ATTRIB_NAME = "AuthnRequestsSigned";
   String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";

   Boolean isAuthnRequestsSigned();

   XSBooleanValue isAuthnRequestsSignedXSBoolean();

   void setAuthnRequestsSigned(Boolean var1);

   void setAuthnRequestsSigned(XSBooleanValue var1);

   Boolean getWantAssertionsSigned();

   XSBooleanValue getWantAssertionsSignedXSBoolean();

   void setWantAssertionsSigned(Boolean var1);

   void setWantAssertionsSigned(XSBooleanValue var1);

   List getAssertionConsumerServices();

   AssertionConsumerService getDefaultAssertionConsumerService();

   List getAttributeConsumingServices();

   AttributeConsumingService getDefaultAttributeConsumingService();
}
