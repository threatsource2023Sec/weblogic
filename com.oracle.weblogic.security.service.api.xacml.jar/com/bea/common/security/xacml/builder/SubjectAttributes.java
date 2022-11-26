package com.bea.common.security.xacml.builder;

public class SubjectAttributes {
   public static final String SUBJECT_ID = "urn:oasis:names:tc:xacml:1.0:subject:subject-id";
   public static final String SUBJECT_CATEGORY = "urn:oasis:names:tc:xacml:1.0:subject-category";
   public static final String SUBJECT_ID_QUALIFIER = "urn:oasis:names:tc:xacml:1.0:subject:subject-id-qualifier";
   public static final String IDD_NAME = "urn:oasis:names:tc:xacml:1.0:subject:iddname";
   public static final String IDCS_APPROLE = "urn:oasis:names:tc:xacml:1.0:subject:application-roles";
   public static final String KEY_INFO = "urn:oasis:names:tc:xacml:1.0:subject:key-info";
   public static final String AUTHENTICATION_TIME = "urn:oasis:names:tc:xacml:1.0:subject:authentication-time";
   public static final String AUTHENTICATION_METHOD = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:authentication-method";
   public static final String REQUEST_TIME = "urn:oasis:names:tc:xacml:1.0:subject:request-time";
   public static final String SESSION_START_TIME = "urn:oasis:names:tc:xacml:1.0:subject:session-start-time";
   public static final String IP_ADDRESS = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:ip-address";
   public static final String DNS_NAME = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:dns-name";
   public static final String GROUP = "urn:oasis:names:tc:xacml:2.0:subject:group";
   public static final String ROLE = "urn:oasis:names:tc:xacml:2.0:subject:role";

   private SubjectAttributes() {
   }
}
