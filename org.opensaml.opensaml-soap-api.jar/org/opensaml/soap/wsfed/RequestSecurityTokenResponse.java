package org.opensaml.soap.wsfed;

import java.util.List;
import javax.xml.namespace.QName;

public interface RequestSecurityTokenResponse extends WSFedObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestSecurityTokenResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponse", "wst");
   String TYPE_LOCAL_NAME = "RequestSecurityTokenResponseType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponseType", "wst");

   AppliesTo getAppliesTo();

   void setAppliesTo(AppliesTo var1);

   List getRequestedSecurityToken();
}
