package weblogic.security.jaspic.servlet;

import java.security.Principal;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.BaseCallbackHandler;
import weblogic.security.debug.SecurityDebugLogger;
import weblogic.security.debug.SecurityLogger;
import weblogic.security.principal.WLSGroupImpl;
import weblogic.security.principal.WLSUserImpl;
import weblogic.security.spi.WLSUser;
import weblogic.servlet.spi.SubjectHandle;

public class JaspicCallbackHandler extends BaseCallbackHandler {
   private Context context;
   private SecurityLogger logger;

   public JaspicCallbackHandler(Context context) {
      this(context, new SecurityDebugLogger("DebugSecurityAtn"));
   }

   public JaspicCallbackHandler(Context context, SecurityLogger logger) {
      this.context = context;
      this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new PasswordValidationCallbackStrategy(), new CallerPrincipalCallbackStrategy(), new GroupPrincipalCallbackStrategy(), new CssPasswordValidationCallbackStrategy(), new WLSPrivateKeyCallbackStrategy(), new WLSTrustStoreCallbackStrategy(), new WLSSecretKeyCallbackStrategy(), new WLSCertStoreCallbackStrategy()});
      this.logger = logger;
   }

   private class WLSSecretKeyCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private WLSSecretKeyCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return false;
      }

      public void handle(Callback callback) {
      }

      // $FF: synthetic method
      WLSSecretKeyCallbackStrategy(Object x1) {
         this();
      }
   }

   private class WLSCertStoreCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private WLSCertStoreCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return false;
      }

      public void handle(Callback callback) {
      }

      // $FF: synthetic method
      WLSCertStoreCallbackStrategy(Object x1) {
         this();
      }
   }

   private class WLSTrustStoreCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private WLSTrustStoreCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return false;
      }

      public void handle(Callback callback) {
      }

      // $FF: synthetic method
      WLSTrustStoreCallbackStrategy(Object x1) {
         this();
      }
   }

   private class WLSPrivateKeyCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private WLSPrivateKeyCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return false;
      }

      public void handle(Callback callback) {
      }

      // $FF: synthetic method
      WLSPrivateKeyCallbackStrategy(Object x1) {
         this();
      }
   }

   private class CssPasswordValidationCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private CssPasswordValidationCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof ServletPasswordValidationCallback;
      }

      public void handle(Callback callback) {
         ServletPasswordValidationCallback pvc = (ServletPasswordValidationCallback)callback;

         try {
            SubjectHandle subjectHandle = JaspicCallbackHandler.this.context.authenticateAndSaveCredential(pvc.getUsername(), new String(pvc.getPassword()), pvc.getRequest(), pvc.getResponse());
            if (subjectHandle == null) {
               JaspicCallbackHandler.this.logger.debug("PasswordValidationCallback: Authentication failed for user: " + pvc.getUsername());
               pvc.setResult(false);
            } else {
               JaspicCallbackHandler.this.logger.debug("PasswordValidationCallback: Authentication succeeded for user: " + pvc.getUsername() + " populating the security context with the resulting Subject.");
               JaspicCallbackHandler.this.context.populateSubject(pvc.getSubject(), subjectHandle);
               pvc.setResult(true);
            }
         } catch (LoginException var4) {
            JaspicCallbackHandler.this.logger.debug("PasswordValidationCallback: LoginException encountered: " + var4.getMessage());
            pvc.setResult(false);
         }

      }

      // $FF: synthetic method
      CssPasswordValidationCallbackStrategy(Object x1) {
         this();
      }
   }

   private class GroupPrincipalCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private GroupPrincipalCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof GroupPrincipalCallback;
      }

      public void handle(Callback callback) {
         GroupPrincipalCallback gpc = (GroupPrincipalCallback)callback;
         if (gpc.getSubject() == null) {
            JaspicCallbackHandler.this.logger.debug("GroupPrincipalCallback: no groups to add.");
         } else {
            String[] var3 = gpc.getGroups();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String groupName = var3[var5];
               JaspicCallbackHandler.this.logger.debug("GroupPrincipalCallback: adding WLSGroupImpl for : " + groupName);
               gpc.getSubject().getPrincipals().add(new WLSGroupImpl(groupName));
            }

         }
      }

      // $FF: synthetic method
      GroupPrincipalCallbackStrategy(Object x1) {
         this();
      }
   }

   private class CallerPrincipalCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private CallerPrincipalCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof CallerPrincipalCallback;
      }

      public void handle(Callback callback) {
         CallerPrincipalCallback cpc = (CallerPrincipalCallback)callback;
         if (cpc.getSubject() != null) {
            this.addSpecifiedPrincipal(cpc);
         }
      }

      private String getSpecifiedPrincipalName(CallerPrincipalCallback cpc) {
         return cpc.getPrincipal() == null ? cpc.getName() : cpc.getPrincipal().getName();
      }

      private void addSpecifiedPrincipal(CallerPrincipalCallback cpc) {
         String name = this.getSpecifiedPrincipalName(cpc);
         if (name != null && !name.equals("")) {
            JaspicCallbackHandler.this.logger.debug("CallerPrincipalCallback: setting WLSUserImpl principal with principal name: " + name);
            Principal newPrincipal = new WLSUserImpl(name);
            ((WLSUserImpl)newPrincipal).setOriginalPrincipal(cpc.getPrincipal());
            Set userPrincipals = cpc.getSubject().getPrincipals(WLSUser.class);
            cpc.getSubject().getPrincipals().removeAll(userPrincipals);
            cpc.getSubject().getPrincipals().add(newPrincipal);
         } else {
            JaspicCallbackHandler.this.logger.debug("CallerPrincipalCallback: using anonymous user since a null principal name was specified.");
            cpc.getSubject().getPrincipals().clear();
         }

      }

      // $FF: synthetic method
      CallerPrincipalCallbackStrategy(Object x1) {
         this();
      }
   }

   private class PasswordValidationCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private PasswordValidationCallbackStrategy() {
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof PasswordValidationCallback && !(callback instanceof ServletPasswordValidationCallback);
      }

      public void handle(Callback callback) {
         PasswordValidationCallback pvc = (PasswordValidationCallback)callback;
         if (pvc.getUsername() != null && pvc.getPassword() != null && pvc.getSubject() != null) {
            try {
               SubjectHandle subjectHandle = JaspicCallbackHandler.this.context.authenticateAndSaveCredential(pvc.getUsername(), pvc.getPassword());
               if (subjectHandle == null) {
                  pvc.setResult(false);
               } else {
                  JaspicCallbackHandler.this.context.populateSubject(pvc.getSubject(), subjectHandle);
                  pvc.setResult(true);
               }
            } catch (LoginException var4) {
               pvc.setResult(false);
            }

         }
      }

      // $FF: synthetic method
      PasswordValidationCallbackStrategy(Object x1) {
         this();
      }
   }

   public interface Context {
      SubjectHandle authenticateAndSaveCredential(String var1, String var2, HttpServletRequest var3, HttpServletResponse var4) throws LoginException;

      SubjectHandle authenticateAndSaveCredential(String var1, char[] var2) throws LoginException;

      void populateSubject(Subject var1, SubjectHandle var2);
   }
}
