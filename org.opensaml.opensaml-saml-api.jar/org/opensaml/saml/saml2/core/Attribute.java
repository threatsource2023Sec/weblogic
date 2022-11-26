package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Attribute extends SAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute", "saml2");
   String TYPE_LOCAL_NAME = "AttributeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeType", "saml2");
   String NAME_ATTTRIB_NAME = "Name";
   String NAME_FORMAT_ATTRIB_NAME = "NameFormat";
   String FRIENDLY_NAME_ATTRIB_NAME = "FriendlyName";
   String UNSPECIFIED = "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified";
   String URI_REFERENCE = "urn:oasis:names:tc:SAML:2.0:attrname-format:uri";
   String BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";

   String getName();

   void setName(String var1);

   String getNameFormat();

   void setNameFormat(String var1);

   String getFriendlyName();

   void setFriendlyName(String var1);

   List getAttributeValues();
}
