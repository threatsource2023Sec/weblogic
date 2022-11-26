package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface AllowPostdating extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "AllowPostdating";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdating", "wst");
   String TYPE_LOCAL_NAME = "AllowPostdatingType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdatingType", "wst");
}
