package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface KeyInfoConfirmationDataType extends SubjectConfirmationData {
   String TYPE_LOCAL_NAME = "KeyInfoConfirmationDataType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "KeyInfoConfirmationDataType", "saml2");

   List getKeyInfos();
}
