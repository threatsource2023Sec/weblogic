package org.opensaml.soap.wsfed;

import java.util.List;
import javax.xml.namespace.QName;

public interface RequestedSecurityToken extends WSFedObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestedSecurityToken";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityToken", "wst");
   String TYPE_LOCAL_NAME = "RequestedSecurityTokenType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityTokenType", "wst");

   List getSecurityTokens();
}
