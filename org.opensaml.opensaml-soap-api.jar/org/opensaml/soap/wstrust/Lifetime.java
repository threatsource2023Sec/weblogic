package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;

public interface Lifetime extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Lifetime";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Lifetime", "wst");
   String TYPE_LOCAL_NAME = "LifetimeType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "LifetimeType", "wst");

   Created getCreated();

   void setCreated(Created var1);

   Expires getExpires();

   void setExpires(Expires var1);
}
