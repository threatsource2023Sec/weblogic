package org.opensaml.saml.saml2.assertion;

public final class SAML2AssertionValidationParameters {
   public static final String STD_PREFIX = "saml2";
   public static final String SC_INFIX = ".SubjectConfirmation";
   public static final String COND_INFIX = ".Conditions";
   public static final String STMT_INFIX = ".Statement";
   public static final String CLOCK_SKEW = "saml2.ClockSkew";
   public static final String CONFIRMED_SUBJECT_CONFIRMATION = "saml2.ConfirmedSubjectConfirmation";
   public static final String SIGNATURE_REQUIRED = "saml2.SignatureRequired";
   public static final String SIGNATURE_VALIDATION_CRITERIA_SET = "saml2.SignatureValidationCriteriaSet";
   public static final String SC_VALID_RECIPIENTS = "saml2.SubjectConfirmation.ValidRecipients";
   public static final String SC_VALID_ADDRESSES = "saml2.SubjectConfirmation.ValidAddresses";
   public static final String SC_HOK_PRESENTER_KEY = "saml2.SubjectConfirmation.HoK.PresenterKey";
   public static final String SC_HOK_PRESENTER_CERT = "saml2.SubjectConfirmation.HoK.PresenterCertificate";
   public static final String SC_HOK_CONFIRMED_KEYINFO = "saml2.SubjectConfirmation.HoK.ConfirmedKeyInfo";
   public static final String COND_VALID_AUDIENCES = "saml2.Conditions.ValidAudiences";
   public static final String COND_ONE_TIME_USE_EXPIRES = "saml2.Conditions.OneTimeUseExpires";

   private SAML2AssertionValidationParameters() {
   }
}
