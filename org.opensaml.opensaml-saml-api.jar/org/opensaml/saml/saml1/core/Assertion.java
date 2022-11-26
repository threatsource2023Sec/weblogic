package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface Assertion extends SignableSAMLObject, Evidentiary {
   @Nonnull
   @NotEmpty
   String DEFAULT_ELEMENT_LOCAL_NAME = "Assertion";
   @Nonnull
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion", "saml1");
   @Nonnull
   @NotEmpty
   String TYPE_LOCAL_NAME = "AssertionType";
   @Nonnull
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionType", "saml1");
   @Nonnull
   @NotEmpty
   String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
   @Nonnull
   @NotEmpty
   String MINORVERSION_ATTRIB_NAME = "MinorVersion";
   @Nonnull
   @NotEmpty
   String ASSERTIONID_ATTRIB_NAME = "AssertionID";
   @Nonnull
   @NotEmpty
   String ISSUER_ATTRIB_NAME = "Issuer";
   @Nonnull
   @NotEmpty
   String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
   @Nonnull
   @NotEmpty
   String ID_ATTRIB_NAME = "AssertionID";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   int getMajorVersion();

   int getMinorVersion();

   void setVersion(@Nullable SAMLVersion var1);

   @Nullable
   String getIssuer();

   void setIssuer(@Nullable String var1);

   @Nullable
   DateTime getIssueInstant();

   void setIssueInstant(@Nullable DateTime var1);

   @Nullable
   Conditions getConditions();

   void setConditions(@Nullable Conditions var1);

   @Nullable
   Advice getAdvice();

   void setAdvice(@Nullable Advice var1);

   @Nonnull
   @NonnullElements
   List getStatements();

   @Nonnull
   @NonnullElements
   List getStatements(@Nonnull QName var1);

   @Nonnull
   @NonnullElements
   List getSubjectStatements();

   @Nonnull
   @NonnullElements
   List getAuthenticationStatements();

   @Nonnull
   @NonnullElements
   List getAuthorizationDecisionStatements();

   @Nonnull
   @NonnullElements
   List getAttributeStatements();
}
