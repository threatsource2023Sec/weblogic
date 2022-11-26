package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AttributeQuery extends SubjectQuery {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQuery", "saml2p");
   String TYPE_LOCAL_NAME = "AttributeQueryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AttributeQueryType", "saml2p");

   List getAttributes();
}
