package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface RequestKET extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestKET";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestKET", "wst");
   String TYPE_LOCAL_NAME = "RequestKETType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestKETType", "wst");
}
