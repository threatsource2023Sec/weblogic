package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;

public interface RequestSecurityTokenResponseCollection extends AttributeExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponseCollection";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollection", "wst");
   String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseCollectionType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollectionType", "wst");

   List getRequestSecurityTokenResponses();
}
