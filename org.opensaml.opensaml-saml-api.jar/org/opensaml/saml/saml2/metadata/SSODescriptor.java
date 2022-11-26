package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;

public interface SSODescriptor extends RoleDescriptor {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SSODescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SSODescriptor", "md");
   String TYPE_LOCAL_NAME = "SSODescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SSODescriptorType", "md");

   List getArtifactResolutionServices();

   ArtifactResolutionService getDefaultArtifactResolutionService();

   List getSingleLogoutServices();

   List getManageNameIDServices();

   List getNameIDFormats();
}
