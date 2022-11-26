package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.Attribute;

public interface RequestedAttribute extends Attribute {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedAttribute";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttribute", "md");
   String TYPE_LOCAL_NAME = "RequestedAttributeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "RequestedAttributeType", "md");
   String IS_REQUIRED_ATTRIB_NAME = "isRequired";

   Boolean isRequired();

   XSBooleanValue isRequiredXSBoolean();

   void setIsRequired(Boolean var1);

   void setIsRequired(XSBooleanValue var1);
}
