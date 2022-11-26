package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;

public interface AffiliationDescriptor extends SignableSAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AffiliationDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptor", "md");
   String TYPE_LOCAL_NAME = "AffiliationDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptorType", "md");
   String OWNER_ID_ATTRIB_NAME = "affiliationOwnerID";
   String ID_ATTRIB_NAME = "ID";

   String getOwnerID();

   String getID();

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   void setOwnerID(String var1);

   void setID(String var1);

   List getMembers();

   List getKeyDescriptors();
}
