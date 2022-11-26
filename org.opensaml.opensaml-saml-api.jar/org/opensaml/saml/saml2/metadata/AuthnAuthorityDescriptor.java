package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnAuthorityDescriptor extends SAMLObject, RoleDescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnAuthorityDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptor", "md");
   String TYPE_LOCAL_NAME = "AuthnAuthorityDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptorType", "md");

   List getAuthnQueryServices();

   List getAssertionIDRequestServices();

   List getNameIDFormats();
}
