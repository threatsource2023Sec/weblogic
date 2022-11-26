package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;

public interface EntitiesDescriptor extends SignableSAMLObject, TimeBoundSAMLObject, CacheableSAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EntitiesDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor", "md");
   String TYPE_LOCAL_NAME = "EntitiesDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptorType", "md");
   QName ELEMENT_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor");
   String ID_ATTRIB_NAME = "ID";
   String NAME_ATTRIB_NAME = "Name";

   String getName();

   void setName(String var1);

   String getID();

   void setID(String var1);

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   List getEntitiesDescriptors();

   List getEntityDescriptors();
}
