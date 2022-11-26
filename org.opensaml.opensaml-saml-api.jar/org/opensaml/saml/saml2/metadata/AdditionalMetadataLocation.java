package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AdditionalMetadataLocation extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AdditionalMetadataLocation";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocation", "md");
   String TYPE_LOCAL_NAME = "AdditionalMetadataLocationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocationType", "md");
   String NAMESPACE_ATTRIB_NAME = "namespace";

   String getLocationURI();

   void setLocationURI(String var1);

   String getNamespaceURI();

   void setNamespaceURI(String var1);
}
