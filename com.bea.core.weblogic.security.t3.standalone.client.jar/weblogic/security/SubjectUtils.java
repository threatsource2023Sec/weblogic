package weblogic.security;

import java.security.Principal;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class SubjectUtils {
   private static final String ANONYMOUS_NAME = "anonymous";

   public static Principal getUserPrincipal(Subject s) {
      return null;
   }

   public static Principal getUserPrincipal(AuthenticatedSubject s) {
      return null;
   }

   public static String getUsername(Subject s) {
      Set principals = s.getPrincipals();
      if (principals == null) {
         return "anonymous";
      } else {
         Iterator it = principals.iterator();

         Principal principal;
         do {
            if (!it.hasNext()) {
               return "anonymous";
            }

            principal = (Principal)it.next();
         } while(principal == null);

         return principal.getName();
      }
   }

   public static String getUsername(AuthenticatedSubject s) {
      Set principals = s.getPrincipals();
      if (principals == null) {
         return "anonymous";
      } else {
         Iterator it = principals.iterator();

         Principal principal;
         do {
            if (!it.hasNext()) {
               return "anonymous";
            }

            principal = (Principal)it.next();
         } while(principal == null);

         return principal.getName();
      }
   }

   public static String getPrincipalNames(Subject subj) {
      return null;
   }

   public static String getPrincipalNames(AuthenticatedSubject subj) {
      return null;
   }

   public static String displaySubject(Subject subject) {
      return null;
   }

   public static String displaySubject(AuthenticatedSubject subject) {
      return null;
   }

   public static Principal getOnePrincipal(AuthenticatedSubject subject, Class type) {
      return null;
   }

   public static boolean compareSubjects(AuthenticatedSubject s1, AuthenticatedSubject s2) {
      return true;
   }

   public static boolean isUserInGroup(Subject s, String group) {
      return false;
   }

   public static boolean isUserInGroup(AuthenticatedSubject s, String group) {
      return false;
   }

   private static boolean isUserInGroup(Set s, String group) {
      return false;
   }

   public static Subject getAnonymousUser() {
      return null;
   }

   public static AuthenticatedSubject getAnonymousSubject() {
      return new AuthenticatedSubject();
   }

   public static boolean isUserAnonymous(Subject subject) {
      checkSubjectNonNull(subject);
      return isUserAnonymous(AuthenticatedSubject.getFromSubject(subject));
   }

   public static boolean isUserAnonymous(AuthenticatedSubject subject) {
      checkSubjectNonNull(subject);
      if (subject == AuthenticatedSubject.ANON) {
         return true;
      } else {
         return subject.getPrincipals().size() == 0;
      }
   }

   public static boolean isUserAnAdministrator(Subject subject) {
      return false;
   }

   public static boolean isUserAnAdministrator(AuthenticatedSubject subject) {
      return false;
   }

   public static boolean doesUserHaveAnyAdminRoles(AuthenticatedSubject subject) {
      return false;
   }

   public static boolean isUserInAdminRoles(AuthenticatedSubject subject, String[] roleNames) {
      return false;
   }

   public static AuthenticatedSubject combineSubjects(AuthenticatedSubject[] subjects) {
      return null;
   }

   public static void checkSubjectNonNull(Object subject) {
      if (subject == null) {
         throw new AssertionError("Security Subject is null");
      }
   }
}
