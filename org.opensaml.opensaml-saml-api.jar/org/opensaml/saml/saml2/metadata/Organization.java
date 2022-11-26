package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Organization extends SAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Organization";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Organization", "md");
   String TYPE_LOCAL_NAME = "OrganizationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationType", "md");

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   List getOrganizationNames();

   List getDisplayNames();

   List getURLs();
}
