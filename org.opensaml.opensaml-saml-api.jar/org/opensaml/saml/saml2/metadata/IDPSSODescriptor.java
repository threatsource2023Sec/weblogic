package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface IDPSSODescriptor extends SSODescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "IDPSSODescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptor", "md");
   String TYPE_LOCAL_NAME = "IDPSSODescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptorType", "md");
   String WANT_AUTHN_REQ_SIGNED_ATTRIB_NAME = "WantAuthnRequestsSigned";

   Boolean getWantAuthnRequestsSigned();

   XSBooleanValue getWantAuthnRequestsSignedXSBoolean();

   void setWantAuthnRequestsSigned(Boolean var1);

   void setWantAuthnRequestsSigned(XSBooleanValue var1);

   List getSingleSignOnServices();

   List getNameIDMappingServices();

   List getAssertionIDRequestServices();

   List getAttributeProfiles();

   List getAttributes();
}
