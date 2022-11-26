package weblogic.servlet.provider;

import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;

public class WlsSubjectHandle implements SubjectHandle {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private AuthenticatedSubject authSubject;
   private Map associatedData = null;

   public WlsSubjectHandle(AuthenticatedSubject subject) {
      this.authSubject = subject;
   }

   public WlsSubjectHandle(AuthenticatedSubject subject, Map associatedData) {
      this.authSubject = subject;
      this.associatedData = associatedData;
   }

   public AuthenticatedSubject getAuthSubject() {
      return this.authSubject;
   }

   public Object getSubject() {
      return this.authSubject;
   }

   public boolean isAdmin() {
      return SubjectUtils.isUserAnAdministrator(this.authSubject);
   }

   public boolean isAnonymous() {
      return SubjectUtils.isUserAnonymous(this.authSubject);
   }

   public boolean isKernel() {
      return SecurityServiceManager.isKernelIdentity(this.authSubject);
   }

   public boolean isInAdminRoles(String[] roles) {
      return SubjectUtils.isUserInAdminRoles(this.authSubject, roles);
   }

   public String getUsername() {
      return SubjectUtils.getUsername(this.authSubject);
   }

   public Principal getPrincipal() {
      return SubjectUtils.getUserPrincipal(this.authSubject);
   }

   public Object run(PrivilegedAction action) {
      return WebServerRegistry.getInstance().getSecurityProvider().runAsForUserCode(this.authSubject, action, KERNEL_ID);
   }

   public Object run(PrivilegedExceptionAction action) throws PrivilegedActionException {
      return WebServerRegistry.getInstance().getSecurityProvider().runAs(this.authSubject, action, KERNEL_ID);
   }

   public Map getAssociatedData() {
      return this.associatedData;
   }
}
