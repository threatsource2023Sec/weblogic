package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeAuthorityDescriptor extends SAMLObject, RoleDescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeAuthorityDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptor", "md");
   String TYPE_LOCAL_NAME = "AttributeAuthorityDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptorType", "md");

   List getAttributeServices();

   List getAssertionIDRequestServices();

   List getNameIDFormats();

   List getAttributeProfiles();

   List getAttributes();
}
