package weblogic.security.auth.login;

import java.io.IOException;
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
import weblogic.security.auth.callback.URLCallback;

public class UsernamePasswordLoginModule implements LoginModule {
   private Subject subject;
   private CallbackHandler callbackHandler = null;
   private Map sharedState = null;
   private Map options = null;
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
         throw new LoginException();
      } else {
         Callback[] callbacks = new Callback[]{new NameCallback("username: "), new PasswordCallback("password: ", false), new URLCallback("URL: ")};

         try {
            this.callbackHandler.handle(callbacks);
            this.username = ((NameCallback)callbacks[0]).getName();
            if (this.username == null) {
               throw new LoginException();
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

            if (this.url == null) {
               this.url = "";
            }
         } catch (IOException var4) {
            this.log("UsernamePasswordLoginModule CallbackHandler Error: " + var4.getMessage());
            throw new LoginException(var4.toString());
         } catch (UnsupportedCallbackException var5) {
            this.log("UsernamePasswordLoginModule CallbackHandler Error: " + var5.getMessage());
            throw new LoginException();
         }

         if (this.url != null) {
            throw new LoginException("URL not handled in MSA");
         } else {
            this.succeeded = true;
            return this.succeeded;
         }
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
      return true;
   }

   private void log(String msg) {
      System.out.println(msg);
   }
}
