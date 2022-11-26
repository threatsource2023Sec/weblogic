package org.opensaml.saml.ext.saml2aslo;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Asynchronous extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Asynchronous";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "Asynchronous", "aslo");
   String TYPE_LOCAL_NAME = "AsynchronousType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol:ext:async-slo", "AsynchronousType", "aslo");
}
