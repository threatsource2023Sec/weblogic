package weblogic.coherence.service.internal.security;

import com.bea.common.security.service.PrincipalValidationService;
import com.tangosol.internal.net.security.AccessAdapter;
import com.tangosol.net.Service;
import com.tangosol.net.security.IdentityAsserter;
import com.tangosol.run.xml.XmlHelper;
import com.tangosol.util.Binary;
import com.tangosol.util.ExternalizableHelper;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.principal.WLSUserImpl;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class CoherenceIdentityAsserter implements IdentityAsserter, AccessAdapter {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final PrincipalValidationService pvs = getPrincipalValidationServiceFromCSS();
   private IdentityAsserter wrappedIA = null;

   public CoherenceIdentityAsserter(String instance) {
      SecurityServiceManager.checkKernelPermission();
      if (!instance.isEmpty()) {
         this.wrappedIA = (IdentityAsserter)XmlHelper.createInstance(XmlHelper.loadXml(instance), this.getClass().getClassLoader(), (XmlHelper.ParameterResolver)null);
      }

   }

   public void doAs(Subject subject, PrivilegedAction action) {
      AuthenticatedSubject as = AuthenticatedSubject.getFromSubject(subject);
      SecurityServiceManager.runAs(KERNEL_ID, as, action);
   }

   public Subject assertIdentity(Object token, Service service) throws SecurityException {
      Object unknown;
      if (token instanceof Binary) {
         unknown = ExternalizableHelper.fromBinary((Binary)token);
         if (unknown instanceof AuthenticatedSubject) {
            AuthenticatedSubject as = new AuthenticatedSubject(true, ((AuthenticatedSubject)unknown).getPrincipals());
            return as.getSubject();
         }
      }

      unknown = null;
      if (this.wrappedIA == null) {
         if (token == null) {
            return SubjectUtils.getAnonymousSubject().getSubject();
         } else {
            throw new SecurityException("No Coherence IdentityAsserter available to assert identity token");
         }
      } else {
         Subject assertedSubject = this.wrappedIA.assertIdentity(token, service);
         return assertedSubject == null ? SubjectUtils.getAnonymousSubject().getSubject() : this.convertSubjectToAS(assertedSubject).getSubject();
      }
   }

   private AuthenticatedSubject convertSubjectToAS(Subject subject) {
      Set principals = subject.getPrincipals();
      if (principals.size() == 0) {
         return SubjectUtils.getAnonymousSubject();
      } else {
         Iterator iter = principals.iterator();
         Principal user = (Principal)iter.next();
         iter.remove();
         principals.add(new WLSUserImpl(user.getName()));
         pvs.sign(principals);
         return new AuthenticatedSubject(subject);
      }
   }

   private static PrincipalValidationService getPrincipalValidationServiceFromCSS() {
      try {
         return SecurityServiceManager.getPrincipalValidationService(KERNEL_ID);
      } catch (Exception var1) {
         throw new IllegalStateException(var1);
      }
   }
}
