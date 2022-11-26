package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;

public interface SubjectQuery extends Query {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQuery", "saml1p");
   String TYPE_LOCAL_NAME = "SubjectQueryAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQueryAbstractType", "saml1p");

   Subject getSubject();

   void setSubject(Subject var1);
}
