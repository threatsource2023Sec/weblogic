package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;

public interface PDPDescriptor extends RoleDescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PDPDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "PDPDescriptor", "md");
   String TYPE_LOCAL_NAME = "PDPDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "PDPDescriptorType", "md");

   List getAuthzServices();

   List getAssertionIDRequestServices();

   List getNameIDFormats();
}
