package com.bea.security.utils.subject;

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.security.principal.IdentityDomainPrincipal;

public class SubjectUtils {
   public static String displaySubject(Subject subject) {
      if (subject == null) {
         return null;
      } else {
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
   }
}
