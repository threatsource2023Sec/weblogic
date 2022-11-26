package weblogic.security;

import java.security.Principal;
import weblogic.security.subject.SubjectManager;

public class WLSPrincipals {
   private static final String USERS_GROUPNAME = "users";
   private static final String ADMINISTRATORS_GROUPNAME = "Administrators";
   private static final String EVERYONE_GROUPNAME = "everyone";
   private static final String ANONYMOUS_ROLENAME = "Anonymous";
   private static final Principal ANONYMOUS_USER_PRINCIPAL;

   public static String getAnonymousUsername() {
      return SubjectManager.ANONYMOUS_USERNAME;
   }

   public static String getAdministratorsGroupname() {
      return "Administrators";
   }

   public static String getUsersGroupname() {
      return "users";
   }

   public static String getEveryoneGroupname() {
      return "everyone";
   }

   public static String getAnonymousRolename() {
      return "Anonymous";
   }

   public static String getKernelUsername() {
      return "<WLS Kernel>";
   }

   public static Principal getAnonymousUserPrincipal() {
      return ANONYMOUS_USER_PRINCIPAL;
   }

   public static boolean isAnonymousPrincipal(Principal principal) {
      if (principal == null) {
         return true;
      } else {
         return ANONYMOUS_USER_PRINCIPAL.equals(principal);
      }
   }

   public static boolean isSpecialGroupname(String groupname) {
      return "everyone".equals(groupname) || "users".equals(groupname);
   }

   public static boolean isAnonymousRolename(String rolename) {
      return "Anonymous".equals(rolename);
   }

   public static boolean isSpecialUsername(String username) {
      return SubjectManager.ANONYMOUS_USERNAME.equals(username) || "<WLS Kernel>".equals(username);
   }

   public static boolean isKernelUsername(String username) {
      return "<WLS Kernel>".equals(username);
   }

   public static boolean isAnonymousUsername(String username) {
      return SubjectManager.ANONYMOUS_USERNAME.equals(username);
   }

   static {
      ANONYMOUS_USER_PRINCIPAL = new SimplePrincipal(SubjectManager.ANONYMOUS_USERNAME);
   }
}
