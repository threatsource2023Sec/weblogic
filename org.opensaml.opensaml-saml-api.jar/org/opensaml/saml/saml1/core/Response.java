package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface Response extends ResponseAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Response", "saml1p");
   String TYPE_LOCAL_NAME = "ResponseAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "ResponseAbstractType", "saml1p");

   Status getStatus();

   void setStatus(Status var1);

   List getAssertions();
}
