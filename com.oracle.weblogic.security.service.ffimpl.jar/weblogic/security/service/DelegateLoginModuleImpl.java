package weblogic.security.service;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;

public final class DelegateLoginModuleImpl implements LoginModule {
   private LoginModule delegate = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final int INITIALIZE = 0;
   private static final int LOGIN = 1;
   private static final int COMMIT = 2;
   private static final int ABORT = 3;
   private static final int LOGOUT = 4;

   private void handleException(String delegateLoginModuleName, Exception e) {
      throw new ProviderException(SecurityLogger.getDelegateLoginModuleError(delegateLoginModuleName), e);
   }

   public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map wrapperedOptions) {
      String delegateLoginModuleName = (String)wrapperedOptions.get("delegateLoginModuleName");
      if (delegateLoginModuleName == null) {
         throw new ProviderException(SecurityLogger.getNullDelegateLoginModule());
      } else {
         try {
            Class delegateClass = Class.forName(delegateLoginModuleName, true, DescriptorClassLoader.getClassLoader());
            this.delegate = (LoginModule)delegateClass.newInstance();
         } catch (ClassNotFoundException var7) {
            this.handleException(delegateLoginModuleName, var7);
         } catch (IllegalAccessException var8) {
            this.handleException(delegateLoginModuleName, var8);
         } catch (InstantiationException var9) {
            this.handleException(delegateLoginModuleName, var9);
         }

         this.delegate.initialize(subject, callbackHandler, sharedState, (Map)wrapperedOptions.get("delegateOptions"));
      }
   }

   public boolean login() throws LoginException {
      return this.executePriv(new loginDelegateAction(this.delegate, 1));
   }

   public boolean commit() throws LoginException {
      return this.executePriv(new loginDelegateAction(this.delegate, 2));
   }

   public boolean abort() throws LoginException {
      return this.executePriv(new loginDelegateAction(this.delegate, 3));
   }

   public boolean logout() throws LoginException {
      return this.executePriv(new loginDelegateAction(this.delegate, 4));
   }

   private void executePrivInitialize(loginDelegateAction action) {
      SecurityServiceManager.runAs(kernelId, kernelId, action);
   }

   private boolean executePriv(loginDelegateAction action) throws LoginException {
      Object theResult = SecurityServiceManager.runAs(kernelId, kernelId, action);
      if (theResult == null) {
         return false;
      } else if (theResult instanceof Boolean) {
         return (Boolean)theResult;
      } else if (theResult instanceof LoginException) {
         throw (LoginException)theResult;
      } else {
         return false;
      }
   }

   private static class loginDelegateAction implements PrivilegedAction {
      private LoginModule delegate = null;
      private int operation = 1;
      private Subject subject = null;
      private CallbackHandler callbackHandler = null;
      private Map sharedState = null;
      private Map wrapperedOptions = null;

      private loginDelegateAction() {
      }

      public loginDelegateAction(LoginModule theDelegate, int theOperation) {
         this.delegate = theDelegate;
         this.operation = theOperation;
      }

      public loginDelegateAction(LoginModule theDelegate, int theOperation, Subject theSubject, CallbackHandler theCallbackHandler, Map theSharedState, Map theWrapperedOptions) {
         this.delegate = theDelegate;
         this.operation = theOperation;
         this.subject = theSubject;
         this.callbackHandler = theCallbackHandler;
         this.sharedState = theSharedState;
         this.wrapperedOptions = theWrapperedOptions;
      }

      public Object run() {
         if (this.delegate == null) {
            return null;
         } else {
            try {
               switch (this.operation) {
                  case 0:
                     this.delegate.initialize(this.subject, this.callbackHandler, this.sharedState, (Map)this.wrapperedOptions.get("delegateOptions"));
                     return null;
                  case 1:
                     return new Boolean(this.delegate.login());
                  case 2:
                     return new Boolean(this.delegate.commit());
                  case 3:
                     return new Boolean(this.delegate.abort());
                  case 4:
                     return new Boolean(this.delegate.logout());
                  default:
                     return null;
               }
            } catch (LoginException var2) {
               return var2;
            }
         }
      }
   }
}
