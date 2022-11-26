package weblogic.jndi.security.internal.client;

import java.security.AccessControlException;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Hashtable;
import javax.naming.Context;
import javax.net.ssl.SSLContext;
import javax.security.auth.Subject;
import weblogic.corba.j2ee.naming.ContextImpl;
import weblogic.jndi.security.SubjectPusher;
import weblogic.security.auth.login.PasswordCredential;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.security.subject.SubjectProxy;

public final class ClientSubjectPusher implements SubjectPusher {
   private static final boolean DEBUG = false;
   private static AbstractSubject kernelId = null;

   public ClientSubjectPusher() {
      try {
         kernelId = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var2) {
      }

   }

   public final void pushSubject(Hashtable env, Context jndi) {
      PasswordCredential pc = getEnvSecurityUser(env);
      if (pc != null) {
         HashSet creds = new HashSet();
         creds.add(pc);
         AbstractSubject sub = new SubjectProxy(new Subject(false, new HashSet(), creds, new HashSet()));
         SubjectManager.getSubjectManager().pushSubject(kernelId, sub);
         if (jndi instanceof ContextImpl) {
            ((ContextImpl)jndi).enableLogoutOnClose();
         }
      }

   }

   public final void popSubject() {
      SubjectManager.getSubjectManager().popSubject(kernelId);
   }

   public void clearSubjectStack() {
      while(SubjectManager.getSubjectManager().getSize() > 0) {
         this.popSubject();
      }

   }

   public static final PasswordCredential getEnvSecurityUser(Hashtable env) throws IllegalArgumentException {
      try {
         Object credentials = env.get("java.naming.security.credentials");
         String principal = (String)env.get("java.naming.security.principal");
         PasswordCredential info = null;
         if (info != null) {
            return info;
         } else if (credentials instanceof PasswordCredential) {
            return (PasswordCredential)credentials;
         } else {
            if (!(credentials instanceof String) && !(credentials instanceof char[])) {
               if (credentials == null) {
                  if (principal != null) {
                     info = new PasswordCredential(principal);
                  }
               } else if (credentials != null && !(credentials instanceof SSLContext)) {
                  throw new IllegalArgumentException("The 'java.naming.security.credentials' property must be either a password String, an instance of PasswordCredential or an instance of SSLContext.");
               }
            } else {
               if (credentials instanceof String) {
                  credentials = ((String)credentials).toCharArray();
               }

               if (principal == null) {
                  throw new IllegalArgumentException("The 'java.naming.security.principal' property has not been specified");
               }

               info = new PasswordCredential(principal, (char[])((char[])credentials));
            }

            if (info != null) {
               env.put("java.naming.security.credentials", info);
            }

            return info;
         }
      } catch (ClassCastException var4) {
         throw new IllegalArgumentException("The 'java.naming.security.credentials' property must be either a password String or an instance of PasswordCredential.");
      }
   }

   static void p(String s) {
      System.err.println("<ClientSecurityManager> " + s);
   }
}
