package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface IDPEntry extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "IDPEntry";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntry", "saml2p");
   String TYPE_LOCAL_NAME = "IDPEntryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntryType", "saml2p");
   String PROVIDER_ID_ATTRIB_NAME = "ProviderID";
   String NAME_ATTRIB_NAME = "Name";
   String LOC_ATTRIB_NAME = "Loc";

   String getProviderID();

   void setProviderID(String var1);

   String getName();

   void setName(String var1);

   String getLoc();

   void setLoc(String var1);
}
