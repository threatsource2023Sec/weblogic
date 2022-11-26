package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface RequestSecurityToken extends ElementExtensibleXMLObject, AttributeExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestSecurityToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken", "wst");
   String TYPE_LOCAL_NAME = "RequestSecurityTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenType", "wst");
   String CONTEXT_ATTRIB_NAME = "Context";

   String getContext();

   void setContext(String var1);
}
