package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface AuthnRequest extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnRequest";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "saml2p");
   String TYPE_LOCAL_NAME = "AuthnRequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequestType", "saml2p");
   String FORCE_AUTHN_ATTRIB_NAME = "ForceAuthn";
   String IS_PASSIVE_ATTRIB_NAME = "IsPassive";
   String PROTOCOL_BINDING_ATTRIB_NAME = "ProtocolBinding";
   String ASSERTION_CONSUMER_SERVICE_INDEX_ATTRIB_NAME = "AssertionConsumerServiceIndex";
   String ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";
   String ATTRIBUTE_CONSUMING_SERVICE_INDEX_ATTRIB_NAME = "AttributeConsumingServiceIndex";
   String PROVIDER_NAME_ATTRIB_NAME = "ProviderName";

   Boolean isForceAuthn();

   XSBooleanValue isForceAuthnXSBoolean();

   void setForceAuthn(Boolean var1);

   void setForceAuthn(XSBooleanValue var1);

   Boolean isPassive();

   XSBooleanValue isPassiveXSBoolean();

   void setIsPassive(Boolean var1);

   void setIsPassive(XSBooleanValue var1);

   String getProtocolBinding();

   void setProtocolBinding(String var1);

   Integer getAssertionConsumerServiceIndex();

   void setAssertionConsumerServiceIndex(Integer var1);

   String getAssertionConsumerServiceURL();

   void setAssertionConsumerServiceURL(String var1);

   Integer getAttributeConsumingServiceIndex();

   void setAttributeConsumingServiceIndex(Integer var1);

   String getProviderName();

   void setProviderName(String var1);

   Subject getSubject();

   void setSubject(Subject var1);

   NameIDPolicy getNameIDPolicy();

   void setNameIDPolicy(NameIDPolicy var1);

   Conditions getConditions();

   void setConditions(Conditions var1);

   RequestedAuthnContext getRequestedAuthnContext();

   void setRequestedAuthnContext(RequestedAuthnContext var1);

   Scoping getScoping();

   void setScoping(Scoping var1);
}
