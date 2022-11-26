package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AffiliateMember extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AffiliateMember";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliateMember", "md");
   String TYPE_LOCAL_NAME = "entityIDType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "entityIDType", "md");

   String getID();

   void setID(String var1);
}
