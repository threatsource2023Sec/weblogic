package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface RequestedAttachedReference extends RequestedReferenceType {
   String ELEMENT_LOCAL_NAME = "RequestedAttachedReference";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedAttachedReference", "wst");
}
