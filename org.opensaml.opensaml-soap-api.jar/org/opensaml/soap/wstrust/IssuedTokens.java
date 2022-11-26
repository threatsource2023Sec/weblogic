package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface IssuedTokens extends RequestSecurityTokenResponseCollection {
   String ELEMENT_LOCAL_NAME = "IssuedTokens";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "IssuedTokens", "wst");
}
