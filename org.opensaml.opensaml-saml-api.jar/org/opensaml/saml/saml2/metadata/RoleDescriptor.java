package org.opensaml.saml.saml2.metadata;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;

public interface RoleDescriptor extends SignableSAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RoleDescriptor";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptor", "md");
   String TYPE_LOCAL_NAME = "RoleDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RoleDescriptorType", "md");
   String ID_ATTRIB_NAME = "ID";
   String PROTOCOL_ENUMERATION_ATTRIB_NAME = "protocolSupportEnumeration";
   String ERROR_URL_ATTRIB_NAME = "errorURL";

   String getID();

   void setID(String var1);

   List getSupportedProtocols();

   boolean isSupportedProtocol(String var1);

   void addSupportedProtocol(String var1);

   void removeSupportedProtocol(String var1);

   void removeSupportedProtocols(Collection var1);

   void removeAllSupportedProtocols();

   String getErrorURL();

   void setErrorURL(String var1);

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   List getKeyDescriptors();

   Organization getOrganization();

   void setOrganization(Organization var1);

   List getContactPersons();

   List getEndpoints();

   List getEndpoints(QName var1);
}
