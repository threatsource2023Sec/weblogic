package org.opensaml.saml.saml1.core;

import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface ResponseAbstractType extends SignableSAMLObject {
   String INRESPONSETO_ATTRIB_NAME = "InResponseTo";
   String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
   String MINORVERSION_ATTRIB_NAME = "MinorVersion";
   String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
   String RECIPIENT_ATTRIB_NAME = "Recipient";
   String ID_ATTRIB_NAME = "ResponseID";

   String getInResponseTo();

   void setInResponseTo(String var1);

   String getID();

   void setID(String var1);

   SAMLVersion getVersion();

   void setVersion(SAMLVersion var1);

   DateTime getIssueInstant();

   void setIssueInstant(DateTime var1);

   String getRecipient();

   void setRecipient(String var1);
}
