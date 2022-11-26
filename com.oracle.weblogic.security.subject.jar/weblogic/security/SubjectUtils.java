package weblogic.security;

import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.principal.IDCSClient;
import weblogic.security.principal.IDCSScope;
import weblogic.security.principal.IdentityDomainPrincipal;
import weblogic.security.principal.WLSPrincipal;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.internal.SubjectRoleDelegate;
import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;
import weblogic.security.subject.SubjectManager;

public class SubjectUtils {
   private static final SubjectRoleDelegate roleDelegate;

   public static Principal getUserPrincipal(Subject s) {
      checkSubjectNonNull(s);
      return getUserPrincipal(AuthenticatedSubject.getFromSubject(s));
   }

   public static Principal getUserPrincipal(AuthenticatedSubject s) {
      checkSubjectNonNull(s);
      Principal p = getOnePrincipal(s, WLSUser.class);
      if (p != null) {
         return p;
      } else {
         p = getOnePrincipal(s, UserInfo.class);
         if (p != null) {
            return p;
         } else {
            p = getOnePrincipal(s, IDCSClient.class);
            if (p != null) {
               return getUserPrincipal(AuthenticatedSubject.ANON);
            } else {
               Set principalSet = s.getPrincipals();
               if (principalSet.isEmpty()) {
                  return null;
               } else {
                  Iterator i = principalSet.iterator();

                  do {
                     if (!i.hasNext()) {
                        return null;
                     }

                     p = (Principal)i.next();
                  } while(p instanceof WLSGroup || p instanceof IDCSScope);

                  return p;
               }
            }
         }
      }
   }

   public static String getUsername(Subject s) {
      checkSubjectNonNull(s);
      return getUsername(AuthenticatedSubject.getFromSubject(s));
   }

   public static String getUsername(AuthenticatedSubject s) {
      checkSubjectNonNull(s);
      if (SecurityManager.isKernelIdentity(s)) {
         return WLSPrincipals.getKernelUsername();
      } else {
         Principal user = getUserPrincipal(s);
         return user == null ? WLSPrincipals.getAnonymousUsername() : user.getName();
      }
   }

   public static String getPrincipalNames(Subject subj) {
      checkSubjectNonNull(subj);
      return getPrincipalNames(AuthenticatedSubject.getFromSubject(subj));
   }

   public static String getPrincipalNames(AuthenticatedSubject subj) {
      checkSubjectNonNull(subj);
      Object[] obj = subj.getPrincipals().toArray();
      if (obj != null && obj.length != 0) {
         StringBuffer buf = new StringBuffer();

         for(int i = 0; i < obj.length; ++i) {
            String userName = ((Principal)obj[i]).getName();
            if (i > 0) {
               buf.append('/');
            }

            if (userName != null) {
               buf.append(userName);
            }
         }

         return buf.toString();
      } else {
         return WLSPrincipals.getAnonymousUsername();
      }
   }

   public static String displaySubject(Subject subject) {
      checkSubjectNonNull(subject);
      return displaySubject(AuthenticatedSubject.getFromSubject(subject));
   }

   public static String displaySubject(AuthenticatedSubject subject) {
      checkSubjectNonNull(subject);
      StringBuffer buf = new StringBuffer("Subject: ");
      Set principals = subject.getPrincipals();
      buf.append(principals.size());
      buf.append("\n");
      Object[] pArray = principals.toArray();

      for(int i = 0; i < pArray.length; ++i) {
         Principal p = (Principal)pArray[i];
         buf.append("\tPrincipal = ");
         buf.append(p.getClass());
         buf.append("(\"");
         String userName = null;
         if (p instanceof IdentityDomainPrincipal) {
            userName = p.toString();
         } else {
            userName = p.getName();
         }

         if (userName != null) {
            buf.append(userName);
         }

         buf.append("\")\n");
      }

      return buf.toString();
   }

   public static Principal getOnePrincipal(AuthenticatedSubject subject, Class type) {
      checkSubjectNonNull(subject);
      return SubjectManager.getOnePrincipal(subject, type);
   }

   public static boolean compareSubjects(AuthenticatedSubject s1, AuthenticatedSubject s2) {
      checkSubjectNonNull(s1);
      checkSubjectNonNull(s2);
      Set set1 = s1.getPrincipals();
      Set set2 = s2.getPrincipals();
      if (set1.size() != set2.size()) {
         return false;
      } else {
         Principal[] principals = (Principal[])((Principal[])set2.toArray(new Principal[set2.size()]));

         int index;
         for(Iterator i1 = set1.iterator(); i1.hasNext(); principals[index] = null) {
            index = indexOf((Principal)i1.next(), principals);
            if (index < 0) {
               return false;
            }
         }

         return true;
      }
   }

   private static int indexOf(Principal p1, Principal[] principals) {
      for(int i = 0; i < principals.length; ++i) {
         Principal p2 = principals[i];
         if (p2 != null && p1.hashCode() == p2.hashCode() && comparePrincipals(p1, p2)) {
            return i;
         }
      }

      return -1;
   }

   private static boolean comparePrincipals(Principal p1, Principal p2) {
      if (p1 != null && p2 != null) {
         if (!p1.getName().equals(p2.getName())) {
            return false;
         } else if (!p1.getClass().isAssignableFrom(p2.getClass())) {
            return false;
         } else {
            if (p1 instanceof WLSPrincipal && p2 instanceof WLSPrincipal) {
               byte[] b1 = ((WLSPrincipal)p1).getSignedData();
               byte[] b2 = ((WLSPrincipal)p2).getSignedData();
               if (b1.length != b2.length) {
                  return false;
               }

               for(int j = 0; j < b1.length; ++j) {
                  if (b1[j] != b2[j]) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean isUserInGroup(Subject s, String group) {
      checkSubjectNonNull(s);
      Set p = s.getPrincipals();
      return isUserInGroup(p, group);
   }

   public static boolean isUserInGroup(AuthenticatedSubject s, String group) {
      checkSubjectNonNull(s);
      Set p = s.getPrincipals();
      return isUserInGroup(p, group);
   }

   private static boolean isUserInGroup(Set s, String group) {
      if ("users".equals(group)) {
         return !s.isEmpty();
      } else if ("everyone".equals(group)) {
         return true;
      } else {
         Iterator i = s.iterator();

         String userName;
         do {
            Object o;
            do {
               if (!i.hasNext()) {
                  return false;
               }

               o = i.next();
            } while(!(o instanceof WLSGroup));

            userName = ((WLSGroup)o).getName();
         } while(userName != group && (userName == null || !userName.equals(group)));

         return true;
      }
   }

   public static Subject getAnonymousUser() {
      return getAnonymousSubject().getSubject();
   }

   public static AuthenticatedSubject getAnonymousSubject() {
      return AuthenticatedSubject.ANON;
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
      return roleDelegate.isUserAnAdministrator(subject);
   }

   public static boolean isUserAnAdministrator(AuthenticatedSubject subject) {
      return roleDelegate.isUserAnAdministrator(subject);
   }

   public static boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, AuthenticatedSubject requestedSubject) {
      return roleDelegate.isAdminPrivilegeEscalation(currentSubject, requestedSubject);
   }

   public static boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, String principalName, String realmName) {
      return roleDelegate.isAdminPrivilegeEscalation(currentSubject, principalName, realmName);
   }

   public static boolean doesUserHaveAnyAdminRoles(AuthenticatedSubject subject) {
      return roleDelegate.doesUserHaveAnyAdminRoles(subject);
   }

   public static boolean isUserInAdminRoles(AuthenticatedSubject subject, String[] roleNames) {
      return roleDelegate.isUserInAdminRoles(subject, roleNames);
   }

   public static void checkSubjectNonNull(Object subject) {
      if (subject == null) {
         throw new AssertionError(SecurityLogger.getIllegalNullSubject());
      }
   }

   public static AuthenticatedSubject combineSubjects(AuthenticatedSubject[] subjects) {
      if (subjects != null && subjects.length != 0) {
         if (subjects.length == 1) {
            return subjects[0];
         } else {
            HashSet set = new HashSet();

            for(int i = 0; i < subjects.length; ++i) {
               Set principalSet = subjects[i].getPrincipals();
               Iterator iterator = principalSet.iterator();

               while(iterator.hasNext()) {
                  Object principal = iterator.next();
                  set.add(principal);
               }
            }

            return new AuthenticatedSubject(true, set);
         }
      } else {
         return getAnonymousSubject();
      }
   }

   public static void setFrom(Subject subject, Subject fromSubject) {
      replaceContents(subject.getPrincipals(), fromSubject.getPrincipals());
      replaceContents(subject.getPublicCredentials(), fromSubject.getPublicCredentials());
      replaceContents(subject.getPrivateCredentials(), fromSubject.getPrivateCredentials());
   }

   public static void replaceContents(Set set, Set newContents) {
      set.clear();
      set.addAll(newContents);
   }

   static {
      try {
         roleDelegate = (SubjectRoleDelegate)Class.forName("weblogic.security.service.internal.SubjectRoleDelegateImpl").newInstance();
      } catch (Throwable var1) {
         throw new IllegalStateException(var1);
      }
   }
}
