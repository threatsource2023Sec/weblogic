package org.opensaml.saml.saml1.core;

import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface RequestAbstractType extends SignableSAMLObject {
   String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
   String MINORVERSION_ATTRIB_NAME = "MinorVersion";
   String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
   String ID_ATTRIB_NAME = "RequestID";

   SAMLVersion getVersion();

   void setVersion(SAMLVersion var1);

   DateTime getIssueInstant();

   void setIssueInstant(DateTime var1);

   String getID();

   void setID(String var1);

   List getRespondWiths();
}
