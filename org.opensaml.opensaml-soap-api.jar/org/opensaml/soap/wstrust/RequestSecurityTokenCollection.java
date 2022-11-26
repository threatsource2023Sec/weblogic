package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;

public interface RequestSecurityTokenCollection extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestSecurityTokenCollection";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollection", "wst");
   String TYPE_LOCAL_NAME = "RequestSecurityTokenCollectionType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollectionType", "wst");

   List getRequestSecurityTokens();
}
