package com.bea.common.security.xacml.builder;

public class EnvironmentAttributes {
   public static final String CURRENT_TIME = "urn:oasis:names:tc:xacml:1.0:environment:current-time";
   public static final String CURRENT_DATE = "urn:oasis:names:tc:xacml:1.0:environment:current-date";
   public static final String CURRENT_DATE_TIME = "urn:oasis:names:tc:xacml:1.0:environment:current-dateTime";
   public static final String ADMIN_IDD = "urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.AdminIdentityDomain";
   public static final String RESOURCE_IDD = "urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.ResourceIdentityDomain";

   private EnvironmentAttributes() {
   }
}
