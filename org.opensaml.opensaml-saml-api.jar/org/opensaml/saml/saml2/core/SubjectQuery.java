package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface SubjectQuery extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SubjectQuery", "saml2p");
   String TYPE_LOCAL_NAME = "SubjectQueryAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SubjectQueryAbstractType", "saml2p");

   Subject getSubject();

   void setSubject(Subject var1);
}
