package weblogic.servlet.security.internal;

import java.util.ArrayList;
import java.util.Arrays;

public final class ResourceConstraint {
   public static final int TG_NONE = 0;
   public static final int TG_INTEGRAL = 1;
   public static final int TG_CONFIDENTIAL = 2;
   private final String id;
   private final String httpMethod;
   private int transportGuarantee = 0;
   private String[] roles = null;
   private boolean forbidden = false;
   private boolean unrestricted = false;
   private boolean loginRequired = false;

   public ResourceConstraint(String pattern, String method) {
      this.id = pattern;
      this.httpMethod = method;
   }

   public void setForbidden() {
      this.forbidden = true;
   }

   public void setUnrestricted() {
      this.unrestricted = true;
   }

   public void setLoginRequired() {
      this.loginRequired = true;
   }

   public void setRoles(String[] s) {
      this.roles = s;
   }

   public boolean isForbidden() {
      return this.forbidden;
   }

   public boolean isUnrestricted() {
      return this.unrestricted;
   }

   public boolean isLoginRequired() {
      return this.loginRequired;
   }

   public int getTransportGuarantee() {
      return this.transportGuarantee;
   }

   public String getHttpMethod() {
      return this.httpMethod;
   }

   public String[] getRoles() {
      return this.roles;
   }

   public String getResourceId() {
      return this.id;
   }

   public void addRoles(String[] newRoles) {
      String[] allRoles = new String[this.roles.length + newRoles.length];
      ArrayList rols = new ArrayList(Arrays.asList(this.roles));
      rols.addAll(Arrays.asList(newRoles));
      rols.toArray(allRoles);
      this.roles = allRoles;
   }

   public void setTransportGuarantee(int tg) {
      this.transportGuarantee = tg;
   }

   static int getTransportGuarantee(String tg) {
      if (tg == null) {
         return 0;
      } else if ("INTEGRAL".equalsIgnoreCase(tg)) {
         return 1;
      } else {
         return "CONFIDENTIAL".equalsIgnoreCase(tg) ? 2 : 0;
      }
   }

   public static class Holder {
      public static final ResourceConstraint ALL_CONSTRAINT = new ResourceConstraint("/*", (String)null);

      static {
         ALL_CONSTRAINT.setLoginRequired();
      }
   }
}
