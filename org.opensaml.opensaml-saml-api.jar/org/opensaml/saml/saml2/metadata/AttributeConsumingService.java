package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeConsumingService extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeConsumingService";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingService", "md");
   String TYPE_LOCAL_NAME = "AttributeConsumingServiceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingServiceType", "md");
   String INDEX_ATTRIB_NAME = "index";
   String IS_DEFAULT_ATTRIB_NAME = "isDefault";

   int getIndex();

   void setIndex(int var1);

   Boolean isDefault();

   XSBooleanValue isDefaultXSBoolean();

   void setIsDefault(Boolean var1);

   void setIsDefault(XSBooleanValue var1);

   List getNames();

   List getDescriptions();

   List getRequestAttributes();
}
