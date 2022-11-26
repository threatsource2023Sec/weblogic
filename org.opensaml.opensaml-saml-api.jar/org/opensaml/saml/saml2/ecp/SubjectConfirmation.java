package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface SubjectConfirmation extends SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmation", "ecp");
   String TYPE_LOCAL_NAME = "SubjectConfirmationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "SubjectConfirmationType", "ecp");
   String METHOD_ATTRIB_NAME = "Method";

   String getMethod();

   void setMethod(String var1);

   SubjectConfirmationData getSubjectConfirmationData();

   void setSubjectConfirmationData(SubjectConfirmationData var1);
}
