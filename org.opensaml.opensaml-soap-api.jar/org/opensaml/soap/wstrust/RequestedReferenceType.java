package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.SecurityTokenReference;

public interface RequestedReferenceType extends WSTrustObject {
   String TYPE_LOCAL_NAME = "RequestedReferenceType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedReferenceType", "wst");

   SecurityTokenReference getSecurityTokenReference();

   void setSecurityTokenReference(SecurityTokenReference var1);
}
