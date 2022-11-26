package weblogic.security;

import java.io.UnsupportedEncodingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import weblogic.kernel.KernelStatus;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.utils.PartitionUtils;

public class SimpleCallbackHandler extends BaseCallbackHandler {
   public SimpleCallbackHandler(String username, char[] password) {
      PasswordCallbackStrategy pwdStrategy = new PasswordCallbackStrategy(password);
      if (KernelStatus.isServer()) {
         if (username != null && !username.isEmpty() && IdentityDomainNamesEncoder.isEncodedNames(username)) {
            this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new IdentityDomainUserCallbackStrategy(IdentityDomainNamesEncoder.decodeNames(username)), pwdStrategy});
         } else {
            String identityDomain = PartitionUtils.getCurrentIdentityDomain();
            if (identityDomain != null) {
               this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new IdentityDomainUserCallbackStrategy(new IdentityDomainNames(username, identityDomain)), pwdStrategy});
            } else {
               this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new NameCallbackStrategy(username), pwdStrategy});
            }
         }
      } else {
         this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new NameCallbackStrategy(username), pwdStrategy});
      }

   }

   public SimpleCallbackHandler(String username, char[] password, String message) {
      this(username, password);
      if (message != null && !message.isEmpty()) {
         this.message = message;
      }

   }

   public SimpleCallbackHandler(String username, byte[] password, String charsetName) {
      this(username, toCharArray(password, charsetName));
   }

   public SimpleCallbackHandler(String username, String identityDomain, char[] password) {
      this(new IdentityDomainNames(username, identityDomain), password);
   }

   public SimpleCallbackHandler(IdentityDomainNames user, char[] password) {
      this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new IdentityDomainUserCallbackStrategy(user), new PasswordCallbackStrategy(password)});
   }

   public SimpleCallbackHandler(IdentityDomainNames user, char[] password, String message) {
      this(user, password);
      if (message != null && !message.isEmpty()) {
         this.message = message;
      }

   }

   /** @deprecated */
   @Deprecated
   public SimpleCallbackHandler(String username, String password) {
      this(username, password == null ? null : password.toCharArray());
   }

   /** @deprecated */
   @Deprecated
   public SimpleCallbackHandler(String username, byte[] password) {
      this(username, password == null ? null : (new String(password)).toCharArray());
   }

   private static char[] toCharArray(byte[] bytes, String charSetName) {
      try {
         return (new String(bytes, charSetName)).toCharArray();
      } catch (UnsupportedEncodingException var3) {
         throw new RuntimeException(var3);
      }
   }

   private class IdentityDomainUserCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private IdentityDomainNames user;

      private IdentityDomainUserCallbackStrategy(IdentityDomainNames user) {
         this.user = user;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof IdentityDomainUserCallback;
      }

      public void handle(Callback callback) {
         IdentityDomainUserCallback iddUserCb = (IdentityDomainUserCallback)callback;
         iddUserCb.setUser(this.user);
      }

      // $FF: synthetic method
      IdentityDomainUserCallbackStrategy(IdentityDomainNames x1, Object x2) {
         this(x1);
      }
   }

   private class PasswordCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private char[] password;

      private PasswordCallbackStrategy(char[] password) {
         this.password = password;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof PasswordCallback;
      }

      public void handle(Callback callback) {
         PasswordCallback pc = (PasswordCallback)callback;
         pc.setPassword(this.password);
      }

      // $FF: synthetic method
      PasswordCallbackStrategy(char[] x1, Object x2) {
         this(x1);
      }
   }

   private class NameCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private String username;

      private NameCallbackStrategy(String username) {
         this.username = username;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof NameCallback;
      }

      public void handle(Callback callback) {
         NameCallback nc = (NameCallback)callback;
         nc.setName(this.username);
      }

      // $FF: synthetic method
      NameCallbackStrategy(String x1, Object x2) {
         this(x1);
      }
   }
}
