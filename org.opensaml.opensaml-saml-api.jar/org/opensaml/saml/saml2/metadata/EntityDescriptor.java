package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;

public interface EntityDescriptor extends SignableSAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EntityDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptor", "md");
   String TYPE_LOCAL_NAME = "EntityDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptorType", "md");
   QName ELEMENT_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntityDescriptor");
   String ID_ATTRIB_NAME = "ID";
   String ENTITY_ID_ATTRIB_NAME = "entityID";

   String getEntityID();

   void setEntityID(String var1);

   String getID();

   void setID(String var1);

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   List getRoleDescriptors();

   List getRoleDescriptors(QName var1);

   List getRoleDescriptors(QName var1, String var2);

   IDPSSODescriptor getIDPSSODescriptor(String var1);

   SPSSODescriptor getSPSSODescriptor(String var1);

   AuthnAuthorityDescriptor getAuthnAuthorityDescriptor(String var1);

   AttributeAuthorityDescriptor getAttributeAuthorityDescriptor(String var1);

   PDPDescriptor getPDPDescriptor(String var1);

   AffiliationDescriptor getAffiliationDescriptor();

   void setAffiliationDescriptor(AffiliationDescriptor var1);

   Organization getOrganization();

   void setOrganization(Organization var1);

   List getContactPersons();

   List getAdditionalMetadataLocations();
}
