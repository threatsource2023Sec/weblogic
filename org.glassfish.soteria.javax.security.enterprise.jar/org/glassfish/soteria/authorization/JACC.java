package org.glassfish.soteria.authorization;

import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBContext;
import javax.security.auth.Subject;
import javax.security.jacc.EJBRoleRefPermission;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.security.jacc.WebResourcePermission;
import javax.security.jacc.WebRoleRefPermission;

public class JACC {
   public static Subject getSubject() {
      return (Subject)getFromContext("javax.security.auth.Subject.container");
   }

   public static boolean isCallerInRole(String role) {
      Subject subject = getSubject();
      if (hasPermission(subject, new WebRoleRefPermission("", role))) {
         return true;
      } else {
         EJBContext ejbContext = EJB.getEJBContext();
         if (ejbContext != null) {
            String ejbName = EJB.getCurrentEJBName(ejbContext);
            return ejbName != null ? hasPermission(subject, new EJBRoleRefPermission(ejbName, role)) : ejbContext.isCallerInRole(role);
         } else {
            return false;
         }
      }
   }

   public static boolean hasAccessToWebResource(String resource, String... methods) {
      return hasPermission(getSubject(), new WebResourcePermission(resource, methods));
   }

   public static Set getAllDeclaredCallerRoles() {
      PermissionCollection permissionCollection = getPermissionCollection(getSubject());
      permissionCollection.implies(new WebRoleRefPermission("", "nothing"));
      permissionCollection.implies(new EJBRoleRefPermission("", "nothing"));
      return filterRoles(permissionCollection);
   }

   public static boolean hasPermission(Subject subject, Permission permission) {
      return getPolicyPrivileged().implies(fromSubject(subject), permission);
   }

   public static PermissionCollection getPermissionCollection(Subject subject) {
      return getPolicyPrivileged().getPermissions(fromSubject(subject));
   }

   private static Policy getPolicyPrivileged() {
      return (Policy)AccessController.doPrivileged(new PrivilegedAction() {
         public Policy run() {
            return Policy.getPolicy();
         }
      });
   }

   public static Set filterRoles(PermissionCollection permissionCollection) {
      Set roles = new HashSet();
      Iterator var2 = Collections.list(permissionCollection.elements()).iterator();

      while(var2.hasNext()) {
         Permission permission = (Permission)var2.next();
         if (isRolePermission(permission)) {
            String role = permission.getActions();
            if (!roles.contains(role) && isCallerInRole(role)) {
               roles.add(role);
            }
         }
      }

      return roles;
   }

   public static ProtectionDomain fromSubject(Subject subject) {
      return new ProtectionDomain(new CodeSource((URL)null, (Certificate[])null), (PermissionCollection)null, (ClassLoader)null, (Principal[])subject.getPrincipals().toArray(new Principal[subject.getPrincipals().size()]));
   }

   public static Object getFromContext(final String contextName) {
      try {
         Object ctx = AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws PolicyContextException {
               return PolicyContext.getContext(contextName);
            }
         });
         return ctx;
      } catch (PrivilegedActionException var2) {
         throw new IllegalStateException(var2.getCause());
      }
   }

   public static boolean isRolePermission(Permission permission) {
      return permission instanceof WebRoleRefPermission || permission instanceof EJBRoleRefPermission;
   }
}
