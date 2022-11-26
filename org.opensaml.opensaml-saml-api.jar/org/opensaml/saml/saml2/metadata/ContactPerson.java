package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface ContactPerson extends SAMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ContactPerson";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ContactPerson", "md");
   String TYPE_LOCAL_NAME = "ContactPersonType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ContactPersonType", "md");
   String CONTACT_TYPE_ATTRIB_NAME = "contactType";

   ContactPersonTypeEnumeration getType();

   void setType(ContactPersonTypeEnumeration var1);

   Extensions getExtensions();

   void setExtensions(Extensions var1);

   Company getCompany();

   void setCompany(Company var1);

   GivenName getGivenName();

   void setGivenName(GivenName var1);

   SurName getSurName();

   void setSurName(SurName var1);

   List getEmailAddresses();

   List getTelephoneNumbers();
}
