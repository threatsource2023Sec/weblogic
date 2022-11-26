package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface Assertion extends SignableSAMLObject, Evidentiary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Assertion";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion", "saml2");
   String TYPE_LOCAL_NAME = "AssertionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionType", "saml2");
   String VERSION_ATTRIB_NAME = "Version";
   String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
   String ID_ATTRIB_NAME = "ID";

   SAMLVersion getVersion();

   void setVersion(SAMLVersion var1);

   DateTime getIssueInstant();

   void setIssueInstant(DateTime var1);

   String getID();

   void setID(String var1);

   Issuer getIssuer();

   void setIssuer(Issuer var1);

   Subject getSubject();

   void setSubject(Subject var1);

   Conditions getConditions();

   void setConditions(Conditions var1);

   Advice getAdvice();

   void setAdvice(Advice var1);

   List getStatements();

   List getStatements(QName var1);

   List getAuthnStatements();

   List getAuthzDecisionStatements();

   List getAttributeStatements();
}
