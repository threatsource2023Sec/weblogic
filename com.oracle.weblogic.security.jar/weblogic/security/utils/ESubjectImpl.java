package weblogic.security.utils;

import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.entitlement.engine.ESubject;
import weblogic.security.SecurityLogger;
import weblogic.security.spi.WLSGroup;

public class ESubjectImpl implements ESubject {
   public static final String EVERYONE_GROUP = "everyone";
   public static final String USERS_GROUP = "users";
   private Subject subject;
   private Map users;
   private Map groups;
   private Map roles;

   public ESubjectImpl(Subject subject) {
      this(subject, (Map)null);
   }

   public ESubjectImpl(Subject subject, Map roles) {
      if (subject == null) {
         throw new NullPointerException(SecurityLogger.getNullSubject());
      } else {
         this.subject = subject;
         this.users = new HashMap();
         this.groups = new HashMap();
         this.roles = roles;
         Iterator p = subject.getPrincipals().iterator();

         while(p.hasNext()) {
            Principal princ = (Principal)p.next();
            String name = princ.getName();
            if (princ instanceof WLSGroup) {
               this.groups.put(name, name);
            } else {
               this.users.put(name, name);
            }
         }

         if (this.users.size() > 0 || this.groups.size() > 0) {
            this.groups.put("users", "users");
         }

         this.groups.put("everyone", "everyone");
      }
   }

   public Subject getSubject() {
      return this.subject;
   }

   public boolean isUser(String userId) {
      return this.users.containsKey(userId);
   }

   public boolean isMemberOf(String groupId) {
      boolean member = this.groups.containsKey(groupId);
      return member;
   }

   public boolean isInRole(String roleName) {
      return this.roles != null && this.roles.containsKey(roleName);
   }

   public String toString() {
      StringBuffer str = new StringBuffer();
      str.append("[Users: ").append(this.toString(this.users.keySet().iterator()));
      str.append("|Groups: ").append(this.toString(this.groups.keySet().iterator()));
      str.append("|Roles: ").append(this.roles == null ? "null" : "not null");
      str.append("]");
      return str.toString();
   }

   private String toString(Iterator values) {
      StringBuffer str = new StringBuffer();
      if (values.hasNext()) {
         str.append(values.next());
      }

      while(values.hasNext()) {
         str.append(",").append(values.next());
      }

      return str.toString();
   }
}
