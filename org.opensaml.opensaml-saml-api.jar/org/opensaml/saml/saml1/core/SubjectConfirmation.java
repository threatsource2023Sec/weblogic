package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface SubjectConfirmation extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation", "saml1");
   String TYPE_LOCAL_NAME = "SubjectConfirmationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationType", "saml1");

   List getConfirmationMethods();

   void setSubjectConfirmationData(XMLObject var1);

   XMLObject getSubjectConfirmationData();

   KeyInfo getKeyInfo();

   void setKeyInfo(KeyInfo var1);
}
