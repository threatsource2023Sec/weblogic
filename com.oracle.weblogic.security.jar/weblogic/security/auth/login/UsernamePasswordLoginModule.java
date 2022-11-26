package weblogic.security.auth.login;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.Authenticate;
import weblogic.security.auth.callback.URLCallback;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.server.GlobalServiceLocator;

public class UsernamePasswordLoginModule implements LoginModule {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private Subject subject;
   private CallbackHandler callbackHandler = null;
   private Map sharedState = null;
   private Map options = null;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
   private String url = null;
   private boolean succeeded = false;
   private boolean commitSucceeded = false;
   private String username = null;
   private String password = null;

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
      this.callbackHandler = callbackHandler;
      this.sharedState = sharedState;
      this.options = options;
      this.subject = subject;
      if (options != null) {
         Object val = options.get("debug");
         if (val != null && ((String)val).equalsIgnoreCase("true")) {
            this.log("UsernamePasswordLoginModule.initialize(), debug enabled");
         }

         val = options.get("URL");
         if (val != null) {
            this.url = (String)val;
            this.log("UsernamePasswordLoginModule.initialize(), URL " + this.url);
         }
      }

   }

   public boolean login() throws LoginException {
      if (this.callbackHandler == null) {
         this.log("UsernamePasswordLoginModule.login(), no callback handler specifed");
         throw new LoginException(SecurityLogger.getNoCallbackHandlerSpecified());
      } else {
         Callback[] callbacks = new Callback[]{new NameCallback("username: "), new PasswordCallback("password: ", false), new URLCallback("URL: ")};

         try {
            this.callbackHandler.handle(callbacks);
            this.username = ((NameCallback)callbacks[0]).getName();
            if (log.isDebugEnabled()) {
               if (this.username == null) {
                  this.log("UsernamePasswordLoginModule.login(), No username");
               } else {
                  this.log("UsernamePasswordLoginModule.login(), username " + this.username);
               }
            }

            if (this.username == null) {
               throw new LoginException(SecurityLogger.getNoUsernameSpecified());
            }

            char[] charPassword = ((PasswordCallback)callbacks[1]).getPassword();
            if (charPassword == null) {
               charPassword = new char[0];
            }

            this.password = new String(charPassword);
            String callbackURL = ((URLCallback)callbacks[2]).getURL();
            if (callbackURL != null) {
               this.url = callbackURL;
            }

            if (log.isDebugEnabled()) {
               if (this.url == null) {
                  this.log("UsernamePasswordLoginModule.login(), No URL");
               } else {
                  this.log("UsernamePasswordLoginModule.login(), URL " + this.url);
               }
            }

            if (this.url == null) {
               this.url = "";
            }
         } catch (IOException var16) {
            this.log("UsernamePasswordLoginModule CallbackHandler Error: " + var16.getMessage());
            throw new LoginException(var16.toString());
         } catch (UnsupportedCallbackException var17) {
            this.log("UsernamePasswordLoginModule CallbackHandler Error: " + var17.getMessage());
            throw new LoginException(SecurityLogger.getErrorCallbackNotAvailable(var17.getCallback().toString()));
         }

         if (this.url != null) {
            boolean pushedSubject = false;
            ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
            env.setProviderUrl(this.url);
            env.setSecurityPrincipal(this.username);
            env.setSecurityCredentials(this.password);

            try {
               AbstractSubject anonymous = SubjectManager.getSubjectManager().getAnonymousSubject();
               if (anonymous != null && kernelId != null) {
                  SubjectManager.getSubjectManager().pushSubject(kernelId, anonymous);
                  pushedSubject = true;
               }

               Authenticate.authenticate(env, this.subject);
            } catch (RemoteException var12) {
               this.log("UsernamePasswordLoginModule Error: Remote Exception on authenticate, " + var12.getMessage());
               throw new LoginException(var12.toString());
            } catch (IOException var13) {
               this.log("UsernamePasswordLoginModule Error: IO Exception on authenticate, " + var13.getMessage());
               throw new LoginException(var13.toString());
            } catch (LoginException var14) {
               this.log("UsernamePasswordLoginModule Error: Login Exception on authenticate, " + var14.getMessage());
               throw new LoginException(var14.toString());
            } finally {
               if (pushedSubject && kernelId != null) {
                  SubjectManager.getSubjectManager().popSubject(kernelId);
               }

            }
         }

         this.succeeded = true;
         return this.succeeded;
      }
   }

   public boolean commit() throws LoginException {
      if (this.succeeded) {
         final PasswordCredential passwordCred = new PasswordCredential(this.username, this.password);
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               UsernamePasswordLoginModule.this.subject.getPrivateCredentials().add(passwordCred);
               return null;
            }
         });
         this.url = null;
         this.commitSucceeded = true;
         return true;
      } else {
         this.username = null;
         this.password = null;
         this.url = null;
         return false;
      }
   }

   public boolean abort() throws LoginException {
      if (!this.succeeded) {
         return false;
      } else {
         if (this.succeeded && !this.commitSucceeded) {
            this.succeeded = false;
            this.username = null;
            this.password = null;
            this.url = null;
         } else {
            this.logout();
         }

         return true;
      }
   }

   public boolean logout() throws LoginException {
      this.succeeded = false;
      this.commitSucceeded = false;
      this.username = null;
      this.password = null;
      this.url = null;

      try {
         Authenticate.logout(this.subject);
      } catch (LoginException var2) {
      } catch (IOException var3) {
      }

      return true;
   }

   private void log(String msg) {
      if (log != null) {
         log.debug(msg);
      } else {
         System.out.println(msg);
      }

   }
}
