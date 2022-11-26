package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface IDPList extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "IDPList";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPList", "saml2p");
   String TYPE_LOCAL_NAME = "IDPListType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPListType", "saml2p");

   List getIDPEntrys();

   GetComplete getGetComplete();

   void setGetComplete(GetComplete var1);
}
