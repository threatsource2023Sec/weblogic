package com.bea.security.xacml.cache.resource;

import com.bea.common.security.utils.HashCodeUtil;
import weblogic.security.spi.Resource;

public class ResourcePolicyIdUtil {
   private static final char[] SPECIAL_CHARS = new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/', '{', '}', '#', '%', '^'};
   private static final Escaping escaper;
   public static final String TOP_RESOURCE_KEY = "";
   private static final String ENTITLEMENT_TOP_RESOURCE_KEY = "top";

   public static Escaping getEscaper() {
      return escaper;
   }

   public static Escaping getSearchEscaper() {
      return new Escaping(SPECIAL_CHARS, '*');
   }

   public static String getPolicyId(Resource resource) {
      return getPolicyId((Resource)resource, (String)null);
   }

   public static String getPolicyId(Resource resource, String role) {
      return getPolicyId(resource != null ? resource.toString() : null, role);
   }

   public static String getPolicyId(String resource) {
      return getPolicyId((String)resource, (String)null);
   }

   public static String getSearchPolicyId(String resource) {
      return "urn:bea:xacml:2.0:entitlement:*" + (resource != null ? escaper.escapeString(resource) : "") + "*";
   }

   public static String getPolicyId(String resource, String role) {
      return "urn:bea:xacml:2.0:entitlement:" + (role != null ? "role:" + escaper.escapeString(role) + ":" : "resource:") + (resource != null ? escaper.escapeString(resource) : "");
   }

   public static String getResourceId(String policyId) {
      if (policyId.startsWith("urn:bea:xacml:2.0:entitlement:resource:")) {
         String id = policyId.substring("urn:bea:xacml:2.0:entitlement:resource:".length());
         if (!isTopResource(id)) {
            return escaper.unescapeString(id);
         }
      }

      return null;
   }

   public static RoleResource getRoleResourceId(String policyId) {
      if (policyId.startsWith("urn:bea:xacml:2.0:entitlement:role:")) {
         String remainder = policyId.substring("urn:bea:xacml:2.0:entitlement:role:".length());
         int idx = remainder.indexOf(":");
         if (idx > 0) {
            String role = remainder.substring(0, idx);
            String resourceId = remainder.substring(idx + 1);
            return new RoleResource(isTopResource(resourceId) ? null : escaper.unescapeString(resourceId), escaper.unescapeString(role));
         }
      }

      return null;
   }

   public static boolean isTopResource(String resourceId) {
      return resourceId == null || "".equals(resourceId) || "top".equals(resourceId);
   }

   static {
      escaper = new Escaping(SPECIAL_CHARS);
   }

   public static class RoleResource {
      private String resourceId;
      private String role;

      public RoleResource(String resourceId, String role) {
         this.resourceId = resourceId;
         this.role = role;
      }

      public String getResourceId() {
         return this.resourceId;
      }

      public String getRole() {
         return this.role;
      }

      public boolean equals(Object other) {
         if (!(other instanceof RoleResource)) {
            return false;
         } else {
            RoleResource o = (RoleResource)other;
            return (this.resourceId == o.resourceId || this.resourceId != null && this.resourceId.equals(o.resourceId)) && (this.role == o.role || this.role != null && this.role.equals(o.role));
         }
      }

      public int hashCode() {
         int result = 23;
         result = HashCodeUtil.hash(result, this.resourceId);
         result = HashCodeUtil.hash(result, this.role);
         return result;
      }
   }
}
