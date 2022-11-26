package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Scoping extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Scoping";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Scoping", "saml2p");
   String TYPE_LOCAL_NAME = "ScopingType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ScopingType", "saml2p");
   String PROXY_COUNT_ATTRIB_NAME = "ProxyCount";

   Integer getProxyCount();

   void setProxyCount(Integer var1);

   IDPList getIDPList();

   void setIDPList(IDPList var1);

   List getRequesterIDs();
}
