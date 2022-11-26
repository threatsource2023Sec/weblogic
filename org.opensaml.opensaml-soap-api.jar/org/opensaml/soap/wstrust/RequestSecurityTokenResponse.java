package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface RequestSecurityTokenResponse extends ElementExtensibleXMLObject, AttributeExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponse";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponse", "wst");
   String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseType", "wst");
   String CONTEXT_ATTRIB_NAME = "Context";

   String getContext();

   void setContext(String var1);
}
