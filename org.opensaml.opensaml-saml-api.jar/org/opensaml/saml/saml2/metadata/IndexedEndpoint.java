package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface IndexedEndpoint extends Endpoint {
   String DEFAULT_ELEMENT_LOCAL_NAME = "IndexedEndpoint";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IndexedEndpoint", "md");
   String TYPE_LOCAL_NAME = "IndexedEndpointType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "IndexedEndpointType", "md");
   String INDEX_ATTRIB_NAME = "index";
   String IS_DEFAULT_ATTRIB_NAME = "isDefault";

   Integer getIndex();

   void setIndex(Integer var1);

   Boolean isDefault();

   XSBooleanValue isDefaultXSBoolean();

   void setIsDefault(Boolean var1);

   void setIsDefault(XSBooleanValue var1);
}
