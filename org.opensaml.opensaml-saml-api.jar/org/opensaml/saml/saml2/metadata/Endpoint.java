package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Endpoint extends SAMLObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Endpoint";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Endpoint", "md");
   String TYPE_LOCAL_NAME = "EndpointType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EndpointType", "md");
   String BINDING_ATTRIB_NAME = "Binding";
   String LOCATION_ATTRIB_NAME = "Location";
   String RESPONSE_LOCATION_ATTRIB_NAME = "ResponseLocation";

   String getBinding();

   void setBinding(String var1);

   String getLocation();

   void setLocation(String var1);

   String getResponseLocation();

   void setResponseLocation(String var1);
}
